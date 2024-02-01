package com.intravan.salesmanagement.core.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

/**
 * Base ViewBinding Activity.
 */
abstract class BaseViewBindingActivity<T : ViewBinding>(
    val bindingFactory: (LayoutInflater) -> T
) : AppCompatActivity() {

    // Binding.
    private var _binding: T? = null
    val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = bindingFactory(layoutInflater)
        setContentView(binding.root)

        // init.
        initScreen(savedInstanceState)
        initObserver()
        initListener()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    protected abstract fun initScreen(savedInstanceState: Bundle?)
    protected abstract fun initObserver()
    protected abstract fun initListener()
}