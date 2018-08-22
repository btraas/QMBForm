package com.devrygreenhouses.qmb.rows.push.nested

import android.app.Activity
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.util.Log
import android.widget.ListView
import android.widget.TextView
import com.devrygreenhouses.qmb.ReflectionTools
import com.devrygreenhouses.qmb.rows.push.CustomFormActivity
import com.devrygreenhouses.qmb.rows.push.PushCell
import com.devrygreenhouses.qmb.rows.push.PushHandler
import com.quemb.qmbform.FormManager
import com.quemb.qmbform.OnFormRowClickListener
import com.quemb.qmbform.R
import com.quemb.qmbform.descriptor.*
import com.quemb.qmbform.view.Cell


abstract class NestedPushHandler<T: NestedElement<*>>(oldActivity: Activity, title: String, val getIconFor: (T) -> Drawable?, val rootElement: T, val valueChangedListener: OnFormRowValueChangedListener)
    : PushHandler<CustomFormActivity>(oldActivity, title), OnFormRowClickListener {

    private val TAG = "NestedPushHandler"
    var _rows: List<RowDescriptor<*>> = ArrayList()
    var selected: T? = null

    var mFormManager: FormManager? = null
    var form: FormDescriptor? = null



    override fun generate(activity: CustomFormActivity) {

        newActivity = activity

        val listView = activity.findViewById<ListView>(R.id.list_view)

        Log.d(TAG, "building form")


        //this.newActivity = activity
        form = buildForm()

        Log.d(TAG, "showing form")

        mFormManager = FormManager()
        mFormManager?.setup(form, listView, activity)
        mFormManager?.onFormRowClickListener = this
        mFormManager?.setOnFormRowValueChangedListener(valueChangedListener)


        Log.d(TAG, "invoking callback")


        onPresent(oldActivity, activity)


    }

    override fun onPresent(oldActivity: Activity, newActivity: CustomFormActivity) {

    }

    abstract fun cloneFor(oldActivity: Activity, title: String, rootElement: T): NestedPushHandler<T>

    //var rootElement: T //? = null


//    constructor(oldActivity: Activity, title: String, rootElement: T): this(oldActivity, title, rootElement.getSubFolders() as List<T>) {
//        this.rootElement = rootElement
//    }

    open fun createViewForElement(rowDescriptor: NestedPushRowDescriptor<T>, element: T): Cell {
        val isFolder = element.isFolder()
        var cell = when {
            isFolder -> PushCell(oldActivity, CustomFormActivity::class, rowDescriptor, this, getIconFor(element))
            else -> FinishCell<T>(oldActivity, rowDescriptor, this, element)
        }
        cell.findViewById<TextView>(R.id.textView)
                .setTextColor(ContextCompat.getColor(oldActivity, ReflectionTools.getColor(oldActivity, "colorPrimary")))

        return cell
    }

    fun buildForm(): FormDescriptor {

        val form = FormDescriptor()
        val subFolders = rootElement.getSubFolders()

        val simpleRows = rootElement.getSimpleRows()


        if(subFolders.count() > 0) {
            val groupTitle = if(simpleRows.count() > 0) "$title categories" else ""
            val groupSection = SectionDescriptor.newInstance("section", groupTitle)

            for(subFolder in subFolders) {
                val row = NestedPushRowDescriptor<T>(subFolder.toString(), subFolder.toString(), getIconFor(subFolder as T), oldActivity,
                        this.cloneFor(newActivity!!, subFolder.toString(), subFolder as T), subFolder as T)
                groupSection.addRow(row)
                //row.addNested(subFolder as T)
            }
            form.addSection(groupSection)
        }


        if(rootElement != null) {
            val groupSection = SectionDescriptor.newInstance("section", "")

            if(simpleRows.count() > 0) {
                for(simple in simpleRows) {
                    val row = NestedPushRowDescriptor<T>(simple.toString(), simple.toString(), getIconFor(simple as T), oldActivity,
                            this.cloneFor(newActivity!!, simple.toString(), simple as T), simple as T)
                    groupSection.addRow(row)
                    //row.addNested(subFolder as T)
                }
                form.addSection(groupSection)
            }

        }

        return form

    }



}