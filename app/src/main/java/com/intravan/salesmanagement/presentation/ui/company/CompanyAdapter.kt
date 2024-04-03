package com.intravan.salesmanagement.presentation.ui.company

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.intravan.salesmanagement.core.presentation.widget.ChoiceModeListAdapter
import com.intravan.salesmanagement.databinding.ViewCompanyItemBinding
import com.intravan.salesmanagement.presentation.model.CompanyDisplayable

/**
 * Adapter.
 */
class CompanyAdapter(
    var itemClickAction: ((CompanyDisplayable.Item) -> Unit)? = null
) : ChoiceModeListAdapter<CompanyDisplayable.Item, ViewCompanyItemBinding>(
    diffCallback = object :
        DiffUtil.ItemCallback<CompanyDisplayable.Item>() {
        override fun areItemsTheSame(
            oldItem: CompanyDisplayable.Item,
            newItem: CompanyDisplayable.Item
        ): Boolean {
            return oldItem.comcode == newItem.comcode
        }

        override fun areContentsTheSame(
            oldItem: CompanyDisplayable.Item,
            newItem: CompanyDisplayable.Item
        ): Boolean {
            return oldItem == newItem
        }
    }
) {

    override fun createBinding(parent: ViewGroup, viewType: Int): ViewCompanyItemBinding {
        return ViewCompanyItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        ).also { binding ->
            // Row 선택.
            binding.root.setOnClickListener {
                getItem(binding.root.tag as Int).run {
                    itemClickAction?.invoke(this)
                }
            }
        }
    }

    override fun bind(
        binding: ViewCompanyItemBinding,
        item: CompanyDisplayable.Item,
        position: Int
    ) {
        binding.root.tag = position

        // 업체명
        binding.textviewCompanyName.text = item.comname
        // 프로그램명
        binding.textviewProgramName.text = item.prgname
        // 회사 고유번호
        binding.textviewComcode.text = item.comcode
        // 대표자명
        binding.textviewBoss.text = item.boss
        // 사업자 번호
        binding.textviewCompanyNumber.text = item.idno
        //주소
        binding.textviewAddress.text = item.address1
    }
}