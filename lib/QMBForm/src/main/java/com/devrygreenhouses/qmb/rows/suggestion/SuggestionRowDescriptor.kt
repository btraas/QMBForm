package com.devrygreenhouses.qmb.rows.suggestion

import android.content.Context
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListAdapter
import com.devrygreenhouses.qmb.CustomCellViewFactory
import com.devrygreenhouses.qmb.FilterableAdapter
import com.quemb.qmbform.annotation.FormValidator
import com.quemb.qmbform.descriptor.RowDescriptor
import com.quemb.qmbform.descriptor.Value
import com.quemb.qmbform.view.Cell
import java.util.ArrayList

class SuggestionRowDescriptor<T>: RowDescriptor<T>, CustomCellViewFactory {

    private var anchorView: View
    private var verticalOffset: Int
    var adapter: ListAdapter


    constructor(tag: String, title: String, value: Value<T>,
                        anchorView: View, verticalOffset: Int, listAdapter: FilterableAdapter)
            : this(tag, title, value, anchorView, verticalOffset, listAdapter, null)

    constructor(tag: String, title: String, value: Value<T>,
                anchorView: View, verticalOffset: Int, arrayAdapter: ArrayAdapter<String>)
            : this(tag, title, value, anchorView, verticalOffset,null, arrayAdapter)


    private constructor(tag: String, title: String, value: Value<T>,
                        anchorView: View, verticalOffset: Int,
                        listAdapter: FilterableAdapter?, arrayAdapter: ArrayAdapter<String>?) {
        this.mTitle = title
        this.mTag = tag
//        this.mRowType = rowType
        this.value = value
        this.mValidators = ArrayList<FormValidator>()

        this.anchorView = anchorView
        this.verticalOffset = verticalOffset
        this.adapter = listAdapter ?: arrayAdapter!!

    }

    override fun createView(ctx: Context): Cell {
        //val row = this as RowDescriptor<*>

        val arrayAdapter = adapter as? ArrayAdapter<String>
        val filterAdapter = adapter as? FilterableAdapter

        return SuggestionCell(ctx, this, anchorView, verticalOffset, adapter)

    }




}