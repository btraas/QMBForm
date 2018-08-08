package com.devrygreenhouses.qmb

import com.quemb.qmbform.descriptor.RowDescriptor

interface RowFactory<T: RowDescriptor<*>> {
    fun buildRow(): T
}