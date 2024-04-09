package com.intravan.salesmanagement.presentation.ui.company

import android.graphics.drawable.InsetDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import com.intravan.salesmanagement.R
import com.intravan.salesmanagement.core.extension.dip
import com.intravan.salesmanagement.core.extension.repeatOnStarted
import com.intravan.salesmanagement.core.presentation.base.BaseViewBindingFragment
import com.intravan.salesmanagement.core.util.DebugLog
import com.intravan.salesmanagement.core.util.autoCleared
import com.intravan.salesmanagement.databinding.FragmentCompanyBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

/**
 * 업체리스트
 */
@AndroidEntryPoint
class CompanyFragment : BaseViewBindingFragment<FragmentCompanyBinding>() {

    // ViewModel.
    private val viewModel: CompanyViewModel by viewModels()

    // RecyclerView Adapter.
    private var adapter by autoCleared<CompanyAdapter>()

    // ViewBinding.
    override fun fragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentCompanyBinding.inflate(inflater, container, false)

    override fun initScreen(view: View, savedInstanceState: Bundle?) {
        // Adapter.
        adapter = CompanyAdapter().also {
            binding.recyclerview.adapter = it
        }

        // RecyclerView Divider.
        context?.let { context ->
            ContextCompat.getDrawable(context, R.drawable.rectangle_recyclerview_divider)?.also {
                val inset = InsetDrawable(it, dip(16), 0, dip(16), 0)
                val decoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
                decoration.setDrawable(inset)
                binding.recyclerview.addItemDecoration(decoration)
            }
        }
    }

    override fun initObserver() {
        handleEvent(viewModel.event)
        handleUiState(viewModel.uiState)
    }

    override fun initListener() {
        // 이전.
        binding.imageviewBack.setOnClickListener {
            navigateToBack()
        }

        /*val textWatcher = object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //DebugLog.w { ">>>>>>>>>>>>>>>>>>>>>>>> beforeTextChanged: $s, $start, $count, $after" }
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                DebugLog.w { ">>>>>>>>>>>>>>>>>>>>>>>> onTextChanged: $s, $start, $before, $count" }
            }

            override fun afterTextChanged(s: Editable?) {
            }
        }
        binding.edittextSearch.addTextChangedListener(textWatcher)
        binding.edittextSearch.doAfterTextChanged {
            DebugLog.w { ">>>>>>>>>>>>>>>>>>>>>>>> doAfterTextChanged: ${it.toString()}" }
        }*/


        // 검색(키패드).
      /*  binding.edittextSearch.setOnClickListener {
            val searchText = (it as? AppCompatEditText)?.text.toString()
            viewModel.acceptIntent(CompanyIntent.SearchClicked(searchText))
        }*/

        binding.edittextSearch.setOnClickListener {
            /*val searchText = binding.edittextSearch.getStringByTrim()
            val searchText = binding.edittextSearch.text.toString()
            */val searchText = binding.edittextSearch.text.toString().trim()
            viewModel.acceptIntent(CompanyIntent.SearchClicked(searchText))
            DebugLog.e { "<<<<<<<SearchClicked ${CompanyIntent.SearchClicked(searchText)}" }
        }
    }

    // Handle Event.
    private fun handleEvent(events: Flow<CompanyEvent>) = repeatOnStarted {
    }

    // Handle UiState.
    private fun handleUiState(uiStates: StateFlow<CompanyUiState>) = repeatOnStarted {
        uiStates.collect {
            when {
                it.isError -> handleFailuare(it)
                else -> handleSuccess(it)
            }
            handleLoading(it)
        }
    }

    // 로딩.
    private fun handleLoading(uiStates: CompanyUiState) {
        binding.layoutResult.layoutResult.isVisible = uiStates.display.searchedItems.isEmpty()
        binding.layoutLoading.layoutLoading.isVisible = uiStates.isLoading
    }

    // 성공.
    private fun handleSuccess(uiStates: CompanyUiState) {
        adapter.submitList(uiStates.display.searchedItems)
    }

    // 실패.
    private fun handleFailuare(@Suppress("UNUSED_PARAMETER") uiStates: CompanyUiState) {
    }
}