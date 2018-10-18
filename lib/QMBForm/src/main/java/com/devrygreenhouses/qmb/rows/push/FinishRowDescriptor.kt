package com.devrygreenhouses.qmb.rows.push

import android.app.Activity
import android.content.Context
import com.devrygreenhouses.qmb.CustomCellViewFactory
import com.quemb.qmbform.descriptor.RowDescriptor
import com.quemb.qmbform.view.Cell

class FinishRowDescriptor<T>(val activity: Activity, val handler: PushHandler<CustomFormActivity>, val value: T): RowDescriptor<T>(), CustomCellViewFactory {
    override fun createView(ctx: Context): Cell {
        return SimpleFinishCell(activity, this, handler, value)
    }

    override fun onViewCreated(cell: Cell) {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}