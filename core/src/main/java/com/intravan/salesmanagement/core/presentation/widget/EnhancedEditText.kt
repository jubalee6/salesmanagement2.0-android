package com.intravan.salesmanagement.core.presentation.widget

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.view.View.OnKeyListener
import android.view.inputmethod.EditorInfo
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.intravan.salesmanagement.core.R
import com.intravan.salesmanagement.core.extension.dip
import com.intravan.salesmanagement.core.extension.inputMethodManager
import com.intravan.salesmanagement.core.extension.px2dip

class EnhancedEditText : AppCompatEditText, View.OnTouchListener, View.OnFocusChangeListener {

    // Action.
    private var mikeAction: (() -> Unit)? = null
    private var clearAction: (() -> Unit)? = null
    private var searchAction: ((text: String) -> Unit)? = null
    private var focusedAction: (() -> Unit)? = null
    private var focusChangeAction: ((hasFocus: Boolean) -> Unit)? = null

    // Drawable.
    private var mikeDrawable: Drawable? = null
    private var clearDrawable: Drawable? = null
    private var searchDrawable: Drawable? = null
    private var previousLeftDrawable: Drawable? = null
    private var previousRightDrawable: Drawable? = null

    // Padding.
    private var originPaddingStart: Int = 0
    private var originPaddingEnd: Int = 0

    // TextWatcher.
    private var textWatcher: TextWatcher? = null
    private var clearTextWatcher: TextWatcher? = null

    constructor(context: Context) : super(context) {
        initLayout()
    }

    constructor(context: Context, attrs: AttributeSet) : super(
        context, attrs
    ) {
        initLayout()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context, attrs, defStyleAttr
    ) {
        initLayout()
    }

    override fun onDetachedFromWindow() {
        mikeAction = null
        clearAction = null
        searchAction = null
        focusedAction = null
        focusChangeAction = null
        mikeDrawable = null
        clearDrawable = null
        searchDrawable = null
        previousLeftDrawable = null
        previousRightDrawable = null
        textWatcher?.let {
            this.removeTextChangedListener(it)
        }

        super.onDetachedFromWindow()
    }

    private fun initLayout() {

        // 원래 패딩 값을 저장.
        originPaddingStart = paddingStart
        originPaddingEnd = paddingEnd

        // 마이크, 지우기 및 검색 Drawable 초기화.
        mikeDrawable = ContextCompat.getDrawable(context, R.drawable.ic_mike)
            ?.let {
                DrawableCompat.wrap(it)
            }
            ?.apply {
                setVisible(false, false)
                setBounds(0, 0, intrinsicWidth, intrinsicHeight)
            }
        clearDrawable = ContextCompat.getDrawable(context, R.drawable.ic_close)
            ?.let {
                DrawableCompat.wrap(it)
            }
            ?.apply {
                setVisible(false, false)
                setBounds(0, 0, intrinsicWidth, intrinsicHeight)
            }
        searchDrawable = ContextCompat.getDrawable(context, R.drawable.ic_search)
            ?.let {
                DrawableCompat.wrap(it)
            }
            ?.apply {
                setVisible(false, false)
                setBounds(0, 0, intrinsicWidth, intrinsicHeight)
            }

        // 텍스트 변경을 감시하는 TextWatcher 초기화.
        textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // 텍스트 길이.
                val textLength = s?.length ?: 0

                // 텍스트가 있는지에 따라 지우기 아이콘 표시/숨김.
                if (clearAction != null && clearDrawable?.isVisible == true && textLength == 0) {
                    changeClearDrawableVisibility(false)
                } else if (clearAction != null && clearDrawable?.isVisible == false && textLength > 0) {
                    changeClearDrawableVisibility(true)
                }

                // 텍스트가 있는지에 따라 마이크/검색 아이콘 표시/숨김.
                if (mikeAction != null && !hasFocus()) {
                    changeMikeDrawableVisibility(true)
                } else if (mikeAction != null && mikeDrawable?.isVisible == true && searchAction != null && textLength > 0) {
                    changeMikeDrawableVisibility(false)
                } else if (mikeAction != null && mikeDrawable?.isVisible != true && textLength == 0) {
                    changeMikeDrawableVisibility(true)
                } else if (searchAction != null && searchDrawable?.isVisible == true && textLength == 0) {
                    changeSearchDrawableVisibility(false)
                } else if (searchAction != null && searchDrawable?.isVisible == false && textLength > 0) {
                    changeSearchDrawableVisibility(true)
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        }

        // TouchListener.
        setOnTouchListener(this)
        // FocusChangeListener.
        onFocusChangeListener = this
        // add textWatcher.
        addTextChangedListener(textWatcher)
    }

    // 터치 이벤트 처리.
    override fun onTouch(view: View, motionEvent: MotionEvent): Boolean {
        val x = motionEvent.x.toInt()
        val mikeWidth = mikeDrawable?.intrinsicWidth ?: 0
        val clearWidth = clearDrawable?.intrinsicWidth ?: 0
        val searchWidth = searchDrawable?.intrinsicWidth ?: 0

        // 아이콘을 터치한 경우 처리.
        if (mikeDrawable?.isVisible == true && x > width - paddingEnd - mikeWidth) {
            // 마이크 아이콘을 터치한 경우 처리.
            if (motionEvent.action == MotionEvent.ACTION_UP) {
                // 키보드 숨김 및 포커스 해제.
                context?.inputMethodManager?.hideSoftInputFromWindow(applicationWindowToken, 0)
                clearFocus()
                // 마이크 액션 실행.
                mikeAction?.invoke()
            }
            return true
        } else if (clearDrawable?.isVisible == true && x < paddingStart + clearWidth) {
            // 지우기 아이콘을 터치한 경우 처리.
            if (motionEvent.action == MotionEvent.ACTION_UP) {
                // TextWatcher 제거.
                removeTextChangedListener(textWatcher)
                clearTextWatcher?.let {
                    removeTextChangedListener(clearTextWatcher)
                }
                // 텍스트를 초기화.
                text = null
                // 지우기 아이콘 숨김.
                changeClearDrawableVisibility(false)
                // 지우기 액션 실행.
                clearAction?.invoke()
                // TextWatcher 다시 추가.
                addTextChangedListener(textWatcher)
                clearTextWatcher?.let {
                    addTextChangedListener(clearTextWatcher)
                }
            }
            return true
        } else if (searchDrawable?.isVisible == true && x > width - paddingEnd - searchWidth) {
            // 검색 아이콘을 터치한 경우 처리.
            if (motionEvent.action == MotionEvent.ACTION_UP) {
                // 검색.
                search(text?.toString()?.trim())
            }
            return true
        } else if (/*!hasFocus() && */focusedAction != null) {
            focusedAction?.invoke()
        }
        return false
    }

    // 포커스 변경 이벤트 처리.
    override fun onFocusChange(view: View?, hasFocus: Boolean) {
        if (mikeAction != null && !hasFocus) {
            // 포커스를 잃고, 마이크 액션이 정의된 경우 마이크 아이콘 표시.
            changeMikeDrawableVisibility(true)
        } else if (searchAction != null && hasFocus && (text?.length ?: 0) > 0) {
            // 포커스를 얻고, 검색 액션과 검색어가 있다면 검색 아이콘 표시.
            changeSearchDrawableVisibility(true)
        } else if (searchAction != null && !hasFocus) {
            // 포커스를 잃고, 검색 액션이 정의된 경우 검색 아이콘 숨김.
            changeSearchDrawableVisibility(false)
        }
        // 포커스 액션 처리.
        if (focusedAction != null && hasFocus) {
            focusedAction?.invoke()
        }
        focusChangeAction?.invoke(hasFocus)
    }

    // 지우기 Drawable의 Visible 상태 변경.
    private fun changeClearDrawableVisibility(isVisible: Boolean) {
        if (isVisible) {
            // 이전 Drawable 이미지 저장.
            if (previousLeftDrawable == null) {
                previousLeftDrawable = compoundDrawables[0]
            }
            post {
                // 패딩을 8dp로 설정.
                if (px2dip(paddingStart) > 8.0f) {
                    setPadding(dip(8), paddingTop, paddingEnd, paddingBottom)
                }
                // 지우기 아이콘 표시.
                clearDrawable?.setVisible(true, false)
                setCompoundDrawables(clearDrawable, null, compoundDrawables[2], null)
            }
        } else if (mikeAction != null) {
            // 패딩을 원래대로 복구.
            setPadding(originPaddingStart, paddingTop, paddingEnd, paddingBottom)
            // 마이크, 지우기 및 검색 Drawable 이미지 설정.
            mikeDrawable?.setVisible(true, false)
            clearDrawable?.setVisible(false, false)
            searchDrawable?.setVisible(false, false)
            setCompoundDrawables(previousLeftDrawable, null, mikeDrawable, null)
        } else {
            // 패딩을 원래대로 복구.
            setPadding(originPaddingStart, paddingTop, originPaddingEnd, paddingBottom)
            // drawable.
            mikeDrawable?.setVisible(false, false)
            clearDrawable?.setVisible(false, false)
            searchDrawable?.setVisible(false, false)
            setCompoundDrawables(previousLeftDrawable, null, previousRightDrawable, null)
        }
    }

    // 마이크 Drawable의 Visible 상태 변경.
    private fun changeMikeDrawableVisibility(isVisible: Boolean) {
        if (isVisible) {
            // 이전 Drawable 이미지 저장.
            if (previousRightDrawable == null) {
                previousRightDrawable = compoundDrawables[2]
            }
            post {
                // 패딩을 8dp로 설정.
                if (px2dip(paddingEnd) > 8.0f) {
                    setPadding(paddingStart, paddingTop, dip(8), paddingBottom)
                }
                // 마이크, 검색 아이콘 표시/숨김.
                mikeDrawable?.setVisible(true, false)
                searchDrawable?.setVisible(false, false)
                setCompoundDrawables(compoundDrawables[0], null, mikeDrawable, null)
            }
        } else if (searchAction != null) {
            // 마이크, 검색 아이콘 표시/숨김.
            mikeDrawable?.setVisible(false, false)
            searchDrawable?.setVisible(true, false)
            setCompoundDrawables(compoundDrawables[0], null, searchDrawable, null)
        } else {
            // padding 원복.
            setPadding(paddingStart, paddingTop, originPaddingEnd, paddingBottom)
            // drawable.
            mikeDrawable?.setVisible(false, false)
            searchDrawable?.setVisible(false, false)
            setCompoundDrawables(compoundDrawables[0], null, previousRightDrawable, null)
        }
    }

    // 검색 Drawable의 Visible 상태 변경.
    private fun changeSearchDrawableVisibility(isVisible: Boolean) {
        if (isVisible) {
            // 이전 Drawable 이미지 저장.
            if (previousRightDrawable == null) {
                previousRightDrawable = compoundDrawables[2]
            }
            post {
                // 패딩을 8dp로 설정.
                if (px2dip(paddingEnd) > 8.0f) {
                    setPadding(paddingStart, paddingTop, dip(8), paddingBottom)
                }
                // 마이크, 검색 아이콘 표시/숨김.
                mikeDrawable?.setVisible(false, false)
                searchDrawable?.setVisible(true, false)
                setCompoundDrawables(compoundDrawables[0], null, searchDrawable, null)
            }
        } else if (mikeAction != null) {
            // 마이크, 검색 아이콘 표시/숨김.
            mikeDrawable?.setVisible(true, false)
            searchDrawable?.setVisible(false, false)
            setCompoundDrawables(compoundDrawables[0], null, mikeDrawable, null)
        } else {
            // padding 원복.
            setPadding(paddingStart, paddingTop, originPaddingEnd, paddingBottom)
            // drawable.
            mikeDrawable?.setVisible(false, false)
            searchDrawable?.setVisible(false, false)
            setCompoundDrawables(compoundDrawables[0], null, previousRightDrawable, null)
        }
    }

    // 검색.
    private fun search(userInput: String?) {
        context?.inputMethodManager?.hideSoftInputFromWindow(applicationWindowToken, 0)
        clearFocus()
        searchAction?.invoke(userInput ?: "")
    }

    // 마이크 클릭 액션.
    fun setOnMikeClick(action: () -> Unit) {
        mikeAction = action
        changeMikeDrawableVisibility(true)
    }

    // 지우기 클릭 액션.
    fun setOnClearClick(textWatcher: TextWatcher? = null, action: () -> Unit) {
        clearTextWatcher = textWatcher
        clearAction = action
    }

    // 검색 클릭 액션.
    fun setOnSearchClick(action: (text: String) -> Unit) {
        searchAction = action
    }

    // 포커스 변경 액션.
    fun setOnFocusChange(action: (hasFocus: Boolean) -> Unit) {
        focusChangeAction = action
    }

    // 포커스 변경 액션.
    fun setOnFocused(action: () -> Unit) {
        focusedAction = action
    }

    // IME Done 액션 처리를 위한 설정.
    fun onImeDone() {
        imeOptions = EditorInfo.IME_ACTION_DONE
        context?.inputMethodManager?.hideSoftInputFromWindow(applicationWindowToken, 0)
        clearFocus()
    }

    // IME Done 액션 처리를 위한 설정.
    inline fun onImeDone(crossinline block: (text: String) -> Unit) {
        imeOptions = EditorInfo.IME_ACTION_DONE
        onImeAction(block)
    }

    // IME Send 액션 처리를 위한 설정.
    inline fun onImeSend(crossinline block: (text: String) -> Unit) {
        imeOptions = EditorInfo.IME_ACTION_SEND
        onImeAction(block)
    }

    // IME Search 액션 처리를 위한 설정.
    inline fun onImeSearch(crossinline block: (text: String) -> Unit) {
        imeOptions = EditorInfo.IME_ACTION_SEARCH
        onImeAction(block)
    }

    // IME 액션 처리를 위한 설정.
    inline fun onImeAction(crossinline block: (text: String) -> Unit) {
        setOnKeyListener(OnKeyListener { _, keyCode, event ->
            if ((event?.action == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                context?.inputMethodManager?.hideSoftInputFromWindow(applicationWindowToken, 0)
                clearFocus()
                block(text?.toString()?.trim() ?: "")
                return@OnKeyListener true
            }
            false
        })
        setOnEditorActionListener { _, _, _ ->
            context?.inputMethodManager?.hideSoftInputFromWindow(applicationWindowToken, 0)
            clearFocus()
            block(text?.toString()?.trim() ?: "")
            true
        }
    }
}
