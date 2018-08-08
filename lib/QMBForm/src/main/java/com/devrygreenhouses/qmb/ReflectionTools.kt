package com.devrygreenhouses.qmb

import android.app.Activity
import android.app.Application
import android.content.Context
import android.util.Log


/**
 * Created by brayden on 2018-01-20.
 */

object ReflectionTools {


    fun getApplicationName(context: Context): String {
        val applicationInfo = context.applicationInfo
        val stringId = applicationInfo.labelRes
        return if (stringId == 0) applicationInfo.nonLocalizedLabel.toString() else context.getString(stringId)
    }

    fun getMenu(context: Context, menuName: String): Int {
        val _package = context.applicationContext.packageName
//        val clazz = Class.forName(_package + ".R.menu")
//        val _field = clazz.getField(menuName)

        return context.resources.getIdentifier("$_package:menu/$menuName", null, null)

        //return _field.getInt(null)
    }

    fun getLayout(context: Context, name: String): Int {
        val _package = context.applicationContext.packageName

        return context.resources.getIdentifier("$_package:layout/$name", null, null)
    }

    fun getID(context: Context, name: String): Int {


        val _package = context.applicationContext.packageName

        return context.resources.getIdentifier("$_package:id/$name", null, null)

//        return _field.getInt(null)
    }

    fun getIDOrDefault(context: Context, name: String, default: Int): Int {

        try {

            val _package = context.applicationContext.packageName

            return context.resources.getIdentifier("$_package:id/$name", null, null)
        } catch (e: Exception) {
            return default
        }
//        return _field.getInt(null)
    }

    fun getString(context: Context, name: String): Int {
        val _package = context.applicationContext.packageName

        return context.resources.getIdentifier("$_package:string/$name", null, null)
    }

    fun getDrawable(context: Context, name: String): Int {
        val _package = context.applicationContext.packageName

        return context.resources.getIdentifier("$_package:drawable/$name", null, null)


    }

    fun getColor(context: Context, name: String): Int {
        val _package = context.applicationContext.packageName
        return context.resources.getIdentifier("$_package:color/$name", null, null)
    }



}