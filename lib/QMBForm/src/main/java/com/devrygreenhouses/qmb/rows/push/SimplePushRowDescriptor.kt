package com.devrygreenhouses.qmb.rows.push

import android.app.Activity
import android.content.Context
import android.widget.ListView
import com.quemb.qmbform.FormManager
import com.quemb.qmbform.OnFormRowClickListener
import com.quemb.qmbform.descriptor.*
import com.quemb.qmbform.view.Cell
import com.quemb.qmbform.view.FormBaseCell

class SimplePushRowDescriptor<T>(tag: String, title: String, activity: Activity, val listView: ListView, val items: List<T>, val onSelect: (T) -> Unit)
    : PushRowDescriptor<T>(tag, title, activity, StatusRowHandler<T>(activity, listView, title)) {

    init {
        (handler as StatusRowHandler<T>).pushRowDescriptor= this
    }


    override fun createView(ctx: Context): Cell {
        return PushCell(activity, CustomFormActivity::class, this, handler, null)
    }


    class StatusRowHandler<T>(oldActivity: Activity, val listView: ListView, title: String)
        : PushHandler<CustomFormActivity>(oldActivity, title), OnFormRowClickListener {


        lateinit var pushRowDescriptor: SimplePushRowDescriptor<T>

//        init {
//            this.showRadioButton
//        }

        override fun onFormRowClick(itemDescriptor: FormItemDescriptor?) {

            (itemDescriptor as? FinishRowDescriptor<T>)?.let { finishRowDescriptor ->
                pushRowDescriptor.value = Value(finishRowDescriptor.value)
                (pushRowDescriptor.cell as FormBaseCell).onValueChanged(Value(finishRowDescriptor.value))
                listView.invalidateViews()
                finishRowDescriptor.activity.finish()

                pushRowDescriptor.value?.value?.let {
                    pushRowDescriptor.onSelect(it)
                }

            }
        }

        override fun canPresent(): Boolean {
            return true
        }
        override fun onPresent(oldActivity: Activity, newActivity: CustomFormActivity) {

        }

        override fun generate(activity: CustomFormActivity) {

            val descriptor = FormDescriptor()
            val section = SectionDescriptor.newInstance("status_section")

            descriptor.addSection(section)

            for(item in pushRowDescriptor.items) {
                val row = FinishRowDescriptor<T>(activity, this, item)
                row.title = item.toString()
                row.onFormRowClickListener = this
                section.addRow(row)
            }


            activity.runOnUiThread {
                val mFormManager = FormManager()
                mFormManager?.setup(descriptor, activity.listView, activity)
                mFormManager?.setOnFormRowClickListener(this)
//                mFormManager?.setOnFormRowValueChangedListener(this)
            }


        }

    }
}