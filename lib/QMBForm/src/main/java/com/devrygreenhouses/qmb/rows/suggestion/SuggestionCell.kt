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
import com.quemb.qmbform.descriptor.Value
import com.quemb.qmbform.view.FormEditTextFieldCell

@SuppressLint("ViewConstructor")
/**
 * Created by pmaccamp on 8/28/2015.
 */
class SuggestionCell(context: Context, rowDescriptor: SuggestionRowDescriptor<*>,
                     val anchorView: View, val verticalOffset: Int,
                     var adapter: ListAdapter)
        : FormEditTextFieldCell(context, rowDescriptor) {

    lateinit var autoComplete: CustomAutoCompleteTextView

    var onSelect: ((SuggestionRowDescriptor<*>, Any) -> Unit)? = null

//    constructor(context: Context, rowDescriptor: SuggestionRowDescriptor<*>, anchorView: View,
//                adapter: FilterableAdapter): super(context, rowDescriptor)  {
//
//        this.anchorView = anchorView
//        this.filterAdapter = adapter
//        this.arrayAdapter = null
//
//    }



    override fun init() {
        super.init()
    }


    override fun onEditTextChanged(string: String?) {
        //super.onEditTextChanged(string) // this will trigger a value change, which isn't happening in a suggestionCell.
    }



    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        autoComplete = findViewById(R.id.editText)
        autoComplete.inputType = InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS
        autoComplete.setOverlapAnchor(true)
        autoComplete.setText(rowDescriptor.value?.value?.toString() ?: "")
        autoComplete.onItemClickListener = object : AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener {
            override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                this.onItemSelected(parent, view, position, id)
//                Toast.makeText(context, "item selected! $position", Toast.LENGTH_SHORT).show()
//                val newItem = adapter.getItem(position)
//                onSelect(rowDescriptor as SuggestionRowDescriptor<*>, newItem)
//                rowDescriptor.value = Value(newItem)
//                update()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                //Toast.makeText(context, "item selected! $position", Toast.LENGTH_SHORT).show()
                val newItem = adapter.getItem(position)
                onSelect?.invoke(rowDescriptor as SuggestionRowDescriptor<*>, newItem)
                rowDescriptor.value = Value(newItem)
                update()
            }

        }



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

    override fun update() {

        super.update()

        updateEditView()

//        if (rowDescriptor.disabled!!) {
//            mEditView.setEnabled(false)
//            setTextColor(mEditView, CellDescriptor.COLOR_VALUE_DISABLED)
//        } else
//            mEditView.setEnabled(true)

    }

    override fun updateEditView() {

//        val hint = rowDescriptor.getHint(context)
//        if (hint != null) {
//            mEditView.setHint(hint)
//        }

        //val value = rowDescriptor.value?.value?.toString() ?: "" // as Value<String>
        //this.findViewById<TextView>(R.id.value).text = value
        val value = rowDescriptor.value
        if (value != null && value.value != null) {
            val valueString = value.value.toString()
            editView.setText(valueString)
        }

    }

}
