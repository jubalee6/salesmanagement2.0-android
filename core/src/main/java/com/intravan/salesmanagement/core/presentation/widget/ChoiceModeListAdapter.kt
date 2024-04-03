package com.intravan.salesmanagement.core.presentation.widget

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.intravan.salesmanagement.core.util.DebugLog
import java.lang.ref.WeakReference

/**
 * RecyclerView adapter
 */
abstract class ChoiceModeListAdapter<T, V : ViewBinding>(
    diffCallback: DiffUtil.ItemCallback<T>,
    private val choiceMode: ChoiceMode = ChoiceMode.NONE
) : ListAdapter<T, ChoiceModeListAdapter<T, V>.ChoiceModeViewHolder<V>>(diffCallback) {

    // ViewHolder.
    inner class ChoiceModeViewHolder<out V : ViewBinding>(
        val binding: V
    ) : RecyclerView.ViewHolder(binding.root)

    // Choice Mode.
    enum class ChoiceMode {
        NONE,
        SINGLE,
        MULTIPLE
    }

    // 선택 Positions.
    protected var selectedPositions = mutableListOf<Int>()
        private set

    // 최근 위치.
    @Suppress("MemberVisibilityCanBePrivate")
    var recentPosition = RecyclerView.NO_POSITION
        protected set

    // 현재 위치.
    @Suppress("MemberVisibilityCanBePrivate")
    var currentPosition = RecyclerView.NO_POSITION
        protected set

    // RecyclerView WeakReference.
    protected var parentRef: WeakReference<ViewGroup?>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChoiceModeViewHolder<V> {
        // RecyclerView WeakReference.
        parentRef = WeakReference(parent)
        // Holder.
        return ChoiceModeViewHolder(createBinding(parent, viewType))
    }

    protected abstract fun createBinding(parent: ViewGroup, viewType: Int): V

    override fun onBindViewHolder(holder: ChoiceModeViewHolder<V>, position: Int) {
        bind(holder, holder.binding, getItem(position), position)
    }

    protected open fun bind(holder: ChoiceModeViewHolder<V>, binding: V, item: T, position: Int) {
        bind(holder.binding, getItem(position), position)
    }

    protected abstract fun bind(binding: V, item: T, position: Int)

    override fun getItemViewType(position: Int): Int {
        return position
    }

    /*override fun onCurrentListChanged(
        previousList: List<T>,
        currentList: List<T>
    ) {
        super.onCurrentListChanged(previousList, currentList)

        // 첫 번째 아이템 변경 시 scrollview position을 0으로 설정하여 첫 번째 항목이 보이게 설정.
        if (
            previousList.isNotEmpty() &&
            previousList.getOrNull(0)?.entityID != currentList.getOrNull(0)?.entityID
        ) {
            (parentRef?.get() as? RecyclerView)?.scrollToPosition(0)
        }
    }*/

    // 선택 처리.
    protected fun changeSelection(
        position: Int,
        action: ((isSelected: Boolean, selectedPosition: Int, recentPosition: Int) -> Unit)? = null
    ) {
        // 최근 위치 변경.
        recentPosition = currentPosition
        currentPosition = position

        // 선택.
        if (choiceMode == ChoiceMode.SINGLE) {
            // 선택 항목 처리.
            selectedPositions.clear()
            selectedPositions.add(position)
            onSelected(true, position, RecyclerView.NO_POSITION)

            // 선택 갱신.
            notifyItemChanged(position)
            // 이전 선택 갱신.
            if (recentPosition != RecyclerView.NO_POSITION && recentPosition != position) {
                notifyItemChanged(recentPosition)
            }
        } else if (choiceMode == ChoiceMode.MULTIPLE) {
            // 선택 항목 처리.
            if (selectedPositions.contains(position)) {
                selectedPositions.remove(position)
                onSelected(false, position, recentPosition)
                action?.invoke(false, position, recentPosition)
            } else {
                selectedPositions.add(position)
                onSelected(true, position, recentPosition)
                action?.invoke(true, position, recentPosition)
            }
            // 선택 갱신.
            notifyItemChanged(position)
        }

        // Debug.
        DebugLog.i { ">>>>> POSITION => $position, RECENT_POSITION => $recentPosition" }
    }

    protected open fun onSelected(isSelected: Boolean, selectedPosition: Int, recentPosition: Int) {
    }

    // 선택 항목.
    fun selectedItems(): List<T> {
        return currentList.filterIndexed { index, _ ->
            selectedPositions.contains(index)
        }
    }

    // 선택 지우기.
    fun clearSelect() {
        selectedPositions.clear()
        changeSelection(RecyclerView.NO_POSITION)
    }

    // 선택 유/무.
    fun isSelected(position: Int) = selectedPositions.contains(position)
}