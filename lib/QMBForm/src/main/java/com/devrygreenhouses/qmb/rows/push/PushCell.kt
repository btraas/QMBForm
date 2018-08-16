package com.devrygreenhouses.qmb.rows.push

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.widget.TextView
import com.devrygreenhouses.qmb.rows.push.nested.NestedElement
import com.devrygreenhouses.qmb.rows.push.nested.NestedPushRowDescriptor

import com.quemb.qmbform.view.FormButtonFieldCell
import com.quemb.qmbform.R
import kotlin.reflect.KClass

@SuppressLint("ViewConstructor")
/**
 * Created by pmaccamp on 8/28/2015.
 */
class PushCell<ActivityT: Activity>(oldActivity: Activity, newActivityClass: KClass<ActivityT>, rowDescriptor: PushRowDescriptor<*>, val handler: PushHandler<*>)
    : FormButtonFieldCell(oldActivity, rowDescriptor) {


//    var filterAdapter: FilterableAdapter?
//    var arrayAdapter: ArrayAdapter<String>?

    init {
        this.setOnClickListener {

//            Toast.makeText(activity, "moving to new activity", Toast.LENGTH_SHORT).show()


            val intent = Intent(oldActivity, newActivityClass.java)

            intent.putExtra("handler", PushHandlerPointer(handler))
            oldActivity.startActivityForResult(intent,1 )


        }
    }

    override fun getResource(): Int {
        return R.layout.push_field_cell
    }


    override fun update() {

        super.update()

        updateEditView()

//        if (rowDescriptor.disabled!!) {
//            mEditView.setEnabled(false)
//            setTextColor(mEditView, CellDescriptor.COLOR_VALUE_DISABLED)
//        } else
//            mEditView.setEnabled(true)

    }

    protected fun updateEditView() {

//        val hint = rowDescriptor.getHint(context)
//        if (hint != null) {
//            mEditView.setHint(hint)
//        }

        val value = rowDescriptor.value?.value?.toString() ?: "" // as Value<String>
        this.findViewById<TextView>(R.id.value).text = value
//        if (value != null && value.value != null) {
//            val valueString = value.value
//            mEditView.setText(valueString)
//        }

    }





}
