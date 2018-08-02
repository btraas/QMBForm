package com.devrygreenhouses.qmb.rows.push

import android.app.Activity
import com.quemb.qmbform.descriptor.*
import com.quemb.qmbform.view.Cell


abstract class NestedPushHandler<T: NestedElement>(oldActivity: Activity, title: String, val rootElement: T): PushHandler(oldActivity, title) {

    abstract fun cloneFor(oldActivity: Activity, title: String, rootElement: T): NestedPushHandler<T>

    //var rootElement: T //? = null


//    constructor(oldActivity: Activity, title: String, rootElement: T): this(oldActivity, title, rootElement.getSubFolders() as List<T>) {
//        this.rootElement = rootElement
//    }

    fun createViewForElement(rowDescriptor: PushRowDescriptor<T>, element: NestedElement): Cell {
        val isFolder = element.isFolder()
        var cell = when {
            isFolder -> PushCell(oldActivity, rowDescriptor, this)
            else -> FinishCell(oldActivity, rowDescriptor, this)
        }
        return cell
    }

    override fun buildForm(): FormDescriptor {

        val form = FormDescriptor()
        val subFolders = rootElement.getSubFolders()

        val simpleRows = rootElement.getSimpleRows()


        if(subFolders.count() > 0) {
            val groupTitle = if(simpleRows.count() > 0) "$title categories" else ""
            val groupSection = SectionDescriptor.newInstance("section", groupTitle)

            for(subFolder in subFolders) {
                val row = PushRowDescriptor<T>(subFolder.toString(), subFolder.toString(), oldActivity,
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
                    val row = PushRowDescriptor<T>(simple.toString(), simple.toString(), oldActivity,
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