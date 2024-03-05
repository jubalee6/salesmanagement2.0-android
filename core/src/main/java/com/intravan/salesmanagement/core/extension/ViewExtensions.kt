package com.intravan.salesmanagement.core.extension

import android.graphics.Rect
import android.view.GestureDetector.OnGestureListener
import android.view.MotionEvent
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.GestureDetectorCompat
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager

/**
 * View Extension.
 */

fun View.showSoftInput() {
    context?.inputMethodManager?.showSoftInput(this, 0)
}

fun View.hideSoftInput() {
    context?.inputMethodManager?.hideSoftInputFromWindow(this.applicationWindowToken, 0)
}

fun View.rect() = Rect(left, top, right, bottom)

inline fun View.onFling(crossinline action: () -> Unit) {
    var isFlung = false
    val gestureDetector = GestureDetectorCompat(context,
        object : OnGestureListener {
            override fun onDown(e: MotionEvent): Boolean {
                isFlung = false
                return true
            }

            override fun onShowPress(e: MotionEvent) {}

            override fun onSingleTapUp(e: MotionEvent): Boolean {
                return true
            }

            override fun onScroll(
                e1: MotionEvent?,
                e2: MotionEvent,
                distanceX: Float,
                distanceY: Float
            ): Boolean {
                return true
            }

            override fun onLongPress(e: MotionEvent) {}

            override fun onFling(
                e1: MotionEvent?,
                e2: MotionEvent,
                velocityX: Float,
                velocityY: Float
            ): Boolean {
                if (!isFlung) {
                    action.invoke()
                    isFlung = true
                }
                return true
            }
        }
    )
    setOnTouchListener { view, event ->
        gestureDetector.onTouchEvent(event)
        when (event.action) {
            MotionEvent.ACTION_UP -> view.performClick()
            else -> {}
        }
        true
    }
}

fun View.isVisibleWithAnimation(parentViewGroup: ConstraintLayout, isVisible: Boolean) {
    ConstraintSet()
        .apply {
            val visibility = when (isVisible) {
                true -> View.VISIBLE
                else -> View.GONE
            }
            clone(parentViewGroup)
            setVisibility(id, visibility)
            applyTo(parentViewGroup)
        }
        .run {
            TransitionManager.beginDelayedTransition(parentViewGroup, AutoTransition())
        }
}