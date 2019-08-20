package com.devrygreenhouses.qmb.extensions

import android.graphics.Color
import android.widget.ListView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.quemb.qmbform.R
import com.quemb.qmbform.descriptor.CellDescriptor
import com.quemb.qmbform.descriptor.FormDescriptor
import com.quemb.qmbform.descriptor.RowDescriptor
import java.util.ArrayList


fun FormDescriptor.validateRequired(listView: ListView): Boolean {
    val formRows = ArrayList<RowDescriptor<*>>()
    for(section in this.sections) {
        for(row in section.rows) {

            //val rowVal = row.value?.value
            if(row.required && ((row.value?.value) == null || row.value?.value == "" || row.value?.value?.toString() == "null")) {
                listView.post {
                    row.backgroundColor = ContextCompat.getColor(context!!,  R.color.colorFormError)
                    row.cell?.setBackgroundColor(ContextCompat.getColor(context!!, R.color.colorFormError))

                    val index = row.findIndex(listView)

                    listView.smoothScrollToPosition(index)

                    Toast.makeText(context!!, "Please fill out all required fields.", Toast.LENGTH_LONG).show()

                }


                return false
            }

            val rVal = row.valueData

            listView.post {
                val bg = row.cellConfig[CellDescriptor.BACKGROUND_COLOR] as? Int? ?: Color.WHITE
                row.cell?.setBackgroundColor(bg)
                row.backgroundColor = bg
            }

            formRows.add(row)
        }
    }
    return true
}