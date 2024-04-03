package com.intravan.salesmanagement.core.extension

import android.graphics.drawable.InsetDrawable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.intravan.salesmanagement.core.R
import com.intravan.salesmanagement.core.presentation.widget.CustomDividerItemDecoration

inline fun RecyclerView.beginDragging(crossinline action: () -> Unit) {
    addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            action.invoke()
        }
    })
}

// Add Divider.
fun RecyclerView.addItemDecoration() {

    if (layoutManager !is LinearLayoutManager) {
        return
    }

    ContextCompat.getDrawable(context, R.drawable.rectangle_recyclerview_divider)?.also {
        val inset = InsetDrawable(it, dip(16), 0, dip(16), 0)
        val decoration = CustomDividerItemDecoration(inset)
        addItemDecoration(decoration)
    }
}