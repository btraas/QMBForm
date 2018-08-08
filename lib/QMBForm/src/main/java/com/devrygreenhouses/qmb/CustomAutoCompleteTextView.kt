package com.devrygreenhouses.qmb

import android.content.Context
import android.content.res.Resources
import android.content.res.TypedArray
import android.graphics.Rect
import android.os.Build
import android.support.annotation.RequiresApi
import android.util.AttributeSet
import android.util.Log
import android.view.*
import android.view.inputmethod.CompletionInfo
import android.view.inputmethod.CorrectionInfo
import android.view.inputmethod.EditorInfo
import android.widget.AutoCompleteTextView
import android.widget.ListPopupWindow
import android.widget.Toast

import java.lang.reflect.Field
import java.lang.reflect.Array.setInt
import java.lang.reflect.Modifier


class CustomAutoCompleteTextView: AutoCompleteTextView {

    constructor(ctx: Context): super(ctx)
    constructor(context: Context, attrs: AttributeSet?): super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int): super(context, attrs, defStyleAttr)

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int): super(context, attrs, defStyleAttr, defStyleRes)
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int, popupTheme: Resources.Theme?)
            : super(context, attrs, defStyleAttr, defStyleRes)

//    override fun onAttachedToWindow() {
//        super.onAttachedToWindow()
//    }
//
//    override fun bringToFront() {
//        super.bringToFront()
//    }
//
//    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
//        return super.onKeyDown(keyCode, event)
//    }



    fun getPopupWindow(): ListPopupWindow {
        val field = javaClass.superclass.getDeclaredField("mPopup")
        field.isAccessible = true
        val mPopup = field.get(this) as ListPopupWindow
        return mPopup
    }

    fun getAnchorView(): View {
        return getPopupWindow().anchorView
    }

    fun setOverlapAnchor(overlap: Boolean) {

        var popupWindow = getPopupWindow()

        val setFlag = ListPopupWindow::class.java.getDeclaredField("mOverlapAnchorSet")
        setFlag.isAccessible = true
        setFlag.set(popupWindow, true)

        val overlapFlag = ListPopupWindow::class.java.getDeclaredField("mOverlapAnchor")
        overlapFlag.isAccessible = true
        overlapFlag.set(popupWindow, overlap)

    }

    fun setAnchorView(view: View, verticalOffset: Int) {

        val mPopup = getPopupWindow()
        mPopup.anchorView = view


//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            mPopup.setWindowLayoutType(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
//        }

        mPopup.promptPosition = ListPopupWindow.POSITION_PROMPT_BELOW

        //field.set(this, mPopup)

//        val g = getAnchorView()

//        super.setDropDownHeight(800)
        super.setDropDownVerticalOffset(verticalOffset)
        super.setDropDownWidth(ViewGroup.LayoutParams.MATCH_PARENT)


    }

    override fun showDropDown() {

        setOverlapAnchor(true)

        super.showDropDown()
    }

    override fun getWindowVisibleDisplayFrame(outRect: Rect) {
        super.getWindowVisibleDisplayFrame(outRect)
        //if (mShowDropDownAlwaysAbove)
            outRect.bottom = -3000 // hack for https://github.com/AndroidSDKSources/android-sdk-sources-for-api-level-23/blob/master/android/widget/PopupWindow.java#L1449
    }

    override fun onSelectionChanged(selStart: Int, selEnd: Int) {
        //Toast.makeText(context, "onSelectionChanged!", Toast.LENGTH_SHORT).show()
        super.onSelectionChanged(selStart, selEnd)
    }

    override fun onCommitCompletion(completion: CompletionInfo?) {
        Log.d("CustomAutoComplete", "onCommitCompletion(completion" );
        super.onCommitCompletion(completion)
    }

    override fun onCommitCorrection(info: CorrectionInfo?) {
        Log.d("CustomAutoComplete", "onCommitCompletion(info" );

        super.onCommitCorrection(info)
    }



}