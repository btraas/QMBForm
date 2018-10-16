package com.devrygreenhouses.qmb.rows.push

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.drawable.Drawable
import android.view.ViewManager
import android.widget.ImageView
import android.widget.TextView
import com.devrygreenhouses.qmb.rows.push.nested.NestedElement
import com.devrygreenhouses.qmb.rows.push.nested.NestedPushRowDescriptor

import com.quemb.qmbform.view.FormButtonFieldCell
import com.quemb.qmbform.R
import kotlinx.android.synthetic.main.finish_field_cell.view.*
import kotlin.reflect.KClass

@SuppressLint("ViewConstructor")
/**
 * Created by pmaccamp on 8/28/2015.
 */
class PushCell<ActivityT: Activity>(val oldActivity: Activity, val newActivityClass: KClass<ActivityT>,
                                    rowDescriptor: PushRowDescriptor<*>, val handler: PushHandler<*>, icon: Drawable?, subtitle: String? = null)
    : FormButtonFieldCell(oldActivity, rowDescriptor) {


//    var filterAdapter: FilterableAdapter?
//    var arrayAdapter: ArrayAdapter<String>?

    companion object {
        val CODE_FORM_COMPLETED = 3224
    }


    init {
        this.setOnClickListener {
            onClick()
        }
        this.findViewById<TextView>(R.id.textView).setOnClickListener {
            this.performClick()
        }

        icon?.let {
            this.findViewById<ImageView>(R.id.icon).apply {
                setImageDrawable(it)
                this.setPadding(0,0,20,0)
            }
        }
        subtitle?.let {
            this.findViewById<TextView>(R.id.subtitle).text = it
        }

        val newSubtitle = this.findViewById<TextView>(R.id.subtitle).text.trim()
        if(newSubtitle.isEmpty()) {
            val subtitleView = this.findViewById<TextView>(R.id.subtitle)
            (subtitleView.parent as ViewManager).removeView(subtitleView)
        }


    }

    override fun getResource(): Int {
        return R.layout.push_field_cell
    }

    fun onClick() {

        val canPresent = handler.canPresent()

        if(canPresent) {
            val intent = Intent(oldActivity, newActivityClass.java)

            intent.putExtra("handler", PushHandlerPointer(handler))
            oldActivity.startActivityForResult(intent, CODE_FORM_COMPLETED)
        }


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
