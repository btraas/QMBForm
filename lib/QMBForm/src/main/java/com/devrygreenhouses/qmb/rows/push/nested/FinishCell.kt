package com.devrygreenhouses.qmb.rows.push.nested

import android.annotation.SuppressLint
import android.app.Activity
import android.widget.ImageView
import com.devrygreenhouses.qmb.rows.push.PushHandler

import com.quemb.qmbform.view.FormButtonFieldCell
import com.quemb.qmbform.R

@SuppressLint("ViewConstructor")
/**
 * Created by pmaccamp on 8/28/2015.
 */
class FinishCell<T: NestedElement<*>>(activity: Activity, rowDescriptor: NestedPushRowDescriptor<*>, val handler: NestedPushHandler<T>, val value: T)
    : FormButtonFieldCell(activity, rowDescriptor) {


//    var filterAdapter: FilterableAdapter?
//    var arrayAdapter: ArrayAdapter<String>?

    init {



        this.setOnClickListener {

//            Toast.makeText(activity, "handler.select!", Toast.LENGTH_SHORT).show()

            this.findViewById<ImageView>(R.id.imageView).setImageResource(R.drawable.ic_radio_checked)
//
//            activity.finish()
            handler.onFormRowClick(rowDescriptor)


//            val intent = Intent(activity, CustomFormActivity::class.java)
//
//            intent.putExtra("handler", handler)
//            activity.startActivityForResult(intent,1 )


        }
    }

    @Suppress("UNNECESSARY_SAFE_CALL") // it's actually necessary.
    override fun afterInit() {
        super.afterInit()
        val r = rowDescriptor
        val roValue = r.value
        val h = handler
        val selected = h?.selected
        val v = value
        if(handler?.selected != null && handler?.selected == value) {
            this.findViewById<ImageView>(R.id.imageView).setImageResource(R.drawable.ic_radio_checked)
        }
    }

    override fun getResource(): Int {
        return R.layout.finish_field_cell
    }


    override fun update() {

        super.update()

        updateEditView()

    }

    protected fun updateEditView() {

//        val hint = rowDescriptor.getHint(context)
//        if (hint != null) {
//            mEditView.setHint(hint)
//        }

        val value = rowDescriptor.value?.value
        if(value != null && value == handler.selected)
            this.findViewById<ImageView>(R.id.imageView).setImageResource(R.drawable.ic_radio_checked)
//        if (value != null && value.value != null) {
//            val valueString = value.value
//            mEditView.setText(valueString)
//        }

    }






}
