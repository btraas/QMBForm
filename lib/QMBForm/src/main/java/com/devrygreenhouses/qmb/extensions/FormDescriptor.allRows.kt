package com.devrygreenhouses.qmb.extensions

import com.quemb.qmbform.descriptor.FormDescriptor
import com.quemb.qmbform.descriptor.RowDescriptor

val FormDescriptor.allRows: MutableList<RowDescriptor<*>>
    get() {
        var rows: ArrayList<RowDescriptor<*>> = ArrayList()
        for(section in this.sections) {
            rows.addAll(section.rows)
        }
        return rows
    }