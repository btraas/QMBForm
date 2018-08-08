package com.devrygreenhouses.qmb

import android.content.Context
import android.os.Looper
import android.support.v4.content.ContextCompat
import com.quemb.qmbform.OnFormRowClickListener
import com.quemb.qmbform.R
import com.quemb.qmbform.descriptor.CellDescriptor.COLOR_VALUE
import com.quemb.qmbform.descriptor.FormItemDescriptor
import com.quemb.qmbform.descriptor.RowDescriptor
import com.quemb.qmbform.descriptor.SectionDescriptor

class MaterialSectionDescriptor: SectionDescriptor {

    constructor(tag: String, title: String): super() {
        this.tag = tag
        this.title = title
    }



    override fun addRow(row: RowDescriptor<*>?) {
        super.addRow(row)
    }

    fun initMultiValue(context: Context, buttonText: String, buildNewRow: () -> RowDescriptor<*>) {
        this.isMultivalueSection = true

        val newRow = RowDescriptor.newInstance("add_row", RowDescriptor.FormRowDescriptorTypeButtonInline, buttonText)
        val cellConfig = newRow.cellConfig ?: HashMap<String, Any>()
        cellConfig[COLOR_VALUE] = ContextCompat.getColor(context, R.color.colorPrimary)

        newRow.onFormRowClickListener = OnFormRowClickListener {
            val idx = rowCount
            addRow(buildNewRow(), idx-1)
        }
        if (findRowDescriptor("add_row") == null) {
            addRow(newRow)
        }
    }
}