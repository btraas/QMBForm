package com.devrygreenhouses.qmb.rows.push

import android.annotation.SuppressLint
import android.app.Activity
import com.devrygreenhouses.qmb.rows.push.PushHandler
import com.devrygreenhouses.qmb.rows.push.PushRowDescriptor

import com.quemb.qmbform.view.FormButtonFieldCell
import com.quemb.qmbform.R

@SuppressLint("ViewConstructor")
/**
 * Created by pmaccamp on 8/28/2015.
 */
class FinishCell(activity: Activity, rowDescriptor: PushRowDescriptor<*>, val handler: PushHandler) : FormButtonFieldCell(activity, rowDescriptor) {


//    var filterAdapter: FilterableAdapter?
//    var arrayAdapter: ArrayAdapter<String>?

    init {


        this.setOnClickListener {

            activity.finish()


//            val intent = Intent(activity, CustomFormActivity::class.java)
//
//            intent.putExtra("handler", handler)
//            activity.startActivityForResult(intent,1 )


        }
    }

    override fun getResource(): Int {
        return R.layout.finish_field_cell
    }







}
