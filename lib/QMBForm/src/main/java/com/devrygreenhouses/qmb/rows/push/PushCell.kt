package com.devrygreenhouses.qmb.rows.push

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent

import com.quemb.qmbform.view.FormButtonFieldCell
import com.quemb.qmbform.R

@SuppressLint("ViewConstructor")
/**
 * Created by pmaccamp on 8/28/2015.
 */
class PushCell<T: NestedElement>(activity: Activity, rowDescriptor: PushRowDescriptor<T>, val handler: PushHandler) : FormButtonFieldCell(activity, rowDescriptor) {


//    var filterAdapter: FilterableAdapter?
//    var arrayAdapter: ArrayAdapter<String>?

    init {
        this.setOnClickListener {

            //Toast.makeText(activity, "moving to new activity", Toast.LENGTH_SHORT).show()


            val intent = Intent(activity, CustomFormActivity::class.java)

            intent.putExtra("handler", PushHandlerPointer(handler))
            activity.startActivityForResult(intent,1 )


        }
    }

    override fun getResource(): Int {
        return R.layout.push_field_cell
    }







}
