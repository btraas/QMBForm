package com.devrygreenhouses.qmb.rows.suggestion

import android.content.Context
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.ListAdapter
import com.devrygreenhouses.qmb.CustomCellViewFactory
import com.devrygreenhouses.qmb.FilterableAdapter
import com.devrygreenhouses.qmb.RowFactory
import com.quemb.qmbform.annotation.FormValidator
import com.quemb.qmbform.descriptor.RowDescriptor
import com.quemb.qmbform.descriptor.Value
import com.quemb.qmbform.view.Cell
import java.util.ArrayList

class SuggestionRowDescriptor<T>: RowDescriptor<T>, CustomCellViewFactory, RowFactory<SuggestionRowDescriptor<T>> {
    override fun onViewCreated(cell: Cell) {

    }

    private var anchorView: View
    private var verticalOffset: Int
    var adapter: ListAdapter
    var onSelect: ((SuggestionRowDescriptor<*>, Any) -> Unit)? = null


    constructor(tag: String, title: String, value: Value<T>?,
                        anchorView: View, verticalOffset: Int, listAdapter: FilterableAdapter)
            : this(tag, title, value, anchorView, verticalOffset, listAdapter, null)

    constructor(tag: String, title: String, value: Value<T>?,
                anchorView: View, verticalOffset: Int, arrayAdapter: ArrayAdapter<String>)
            : this(tag, title, value, anchorView, verticalOffset,null, arrayAdapter)


    constructor(tag: String, title: String, value: Value<T>?,
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
        //this.onSelect = onSelect as (SuggestionRowDescriptor<*>, Any) -> Unit

    }

    override fun createView(ctx: Context): Cell {
        //val row = this as RowDescriptor<*>

        val arrayAdapter = adapter as? ArrayAdapter<String>
        val filterAdapter = adapter as? FilterableAdapter

        val c = SuggestionCell(ctx, this, anchorView, verticalOffset, adapter)
        c.onSelect = onSelect
        return c

    }

    fun setCellOnSelect(onSelect: (SuggestionRowDescriptor<T>, Any) -> Unit) {
        this.onSelect = onSelect as ((SuggestionRowDescriptor<*>, Any) -> Unit)
        (this.cell as? SuggestionCell)?.onSelect = this.onSelect
    }

    override fun buildRow(): SuggestionRowDescriptor<T> {
        val newRow = SuggestionRowDescriptor<T>(tag, title, value, anchorView, verticalOffset,
                adapter as? FilterableAdapter, adapter as? ArrayAdapter<String>?)
        newRow.onSelect = onSelect
        return newRow
    }




}