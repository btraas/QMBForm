package com.devrygreenhouses.qmb.rows.suggestion

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.text.InputType
import android.view.View
import android.widget.*
import com.devrygreenhouses.qmb.CustomAutoCompleteTextView
import com.devrygreenhouses.qmb.FilterableAdapter

import com.quemb.qmbform.R
import com.quemb.qmbform.view.FormEditTextFieldCell

@SuppressLint("ViewConstructor")
/**
 * Created by pmaccamp on 8/28/2015.
 */
class SuggestionCell : FormEditTextFieldCell {

    lateinit var autoComplete: CustomAutoCompleteTextView
    val anchorView: View
    val verticalOffset: Int
//    var filterAdapter: FilterableAdapter?
//    var arrayAdapter: ArrayAdapter<String>?
    var adapter: ListAdapter

//    constructor(context: Context, rowDescriptor: SuggestionRowDescriptor<*>, anchorView: View,
//                adapter: FilterableAdapter): super(context, rowDescriptor)  {
//
//        this.anchorView = anchorView
//        this.filterAdapter = adapter
//        this.arrayAdapter = null
//
//    }

    constructor(context: Context, rowDescriptor: SuggestionRowDescriptor<*>, anchorView: View, verticalOffset: Int,
                adapter: ListAdapter)
            : super(context, rowDescriptor)  {

        this.anchorView = anchorView
        this.verticalOffset = verticalOffset
        this.adapter = adapter

    }



    override fun init() {
        super.init()
    }


    override fun onEditTextChanged(string: String?) {
        super.onEditTextChanged(string)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        autoComplete = findViewById(R.id.editText)
        autoComplete.inputType = InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS
        autoComplete.setOverlapAnchor(true)


//        autoComplete.gravity = Gravity.START
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
//            autoComplete.textAlignment = View.TEXT_ALIGNMENT_TEXT_START
//        }
        autoComplete.setAnchorView(anchorView, verticalOffset)
        if(adapter is FilterableAdapter) {
            autoComplete.setAdapter(adapter as FilterableAdapter)
        } else {
            autoComplete.setAdapter(adapter as ArrayAdapter<String>)
        }
        //val anchor = autoComplete.getAnchorView()
        //print(anchorView.layoutParams)
    }

    override fun onCellSelected() {

        super.onCellSelected()
        this.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 500)
    }

    override fun getResource(): Int {
        return R.layout.edit_text_auto_complete_inline_field_cell
    }

    fun setAnchorView(view: View, verticalOffset: Int) {
        autoComplete.setAnchorView(view, verticalOffset)
    }

}
