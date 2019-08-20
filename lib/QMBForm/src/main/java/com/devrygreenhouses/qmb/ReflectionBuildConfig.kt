package com.devrygreenhouses.qmb

import android.content.Context

/**
 * Created by brayden on 2018-01-20.
 */
object ReflectionBuildConfig {
    fun DEBUG(ctx: Context): Boolean {
        val _package = ctx.applicationContext.packageName
        val clazz = Class.forName(_package + ".BuildConfig")
        val debugField = clazz.getField("DEBUG")

        val debug = debugField.getBoolean(null)

        return debug

    }


    fun APPLICATION_ID(ctx: Context): String {
        val _package = ctx.applicationContext.packageName
        val clazz = Class.forName(_package + ".BuildConfig")
        val _field = clazz.getField("APPLICATION_ID")

        return _field.get(null) as String

    }

    fun VERSION_CODE(ctx: Context): Int {
        val _package = ctx.applicationContext.packageName
        val clazz = Class.forName(_package + ".BuildConfig")
        val _field = clazz.getField("VERSION_CODE")

        return (_field.get(null) as Int)

    }

    fun VERSION_NAME(ctx: Context): String {
        val _package = ctx.applicationContext.packageName
        val clazz = Class.forName(_package + ".BuildConfig")
        val _field = clazz.getField("VERSION_NAME")

        return _field.get(null) as String

    }

    fun API_VERSION(ctx: Context): String {
        val _package = ctx.applicationContext.packageName
        val clazz = Class.forName(_package + ".BuildConfig")
        val _field = clazz.getField("API_VERSION")

        return _field.get(null) as String

    }

    fun API_URL(ctx: Context): String {
        val _package = ctx.applicationContext.packageName
        val clazz = Class.forName(_package + ".BuildConfig")
        val _field = clazz.getField("API_URL")

        val apiUrl =  _field.get(null) as String
        return apiUrl

    }

    fun LOG_URL(ctx: Context): String {
        val _package = ctx.applicationContext.packageName
        val clazz = Class.forName(_package + ".BuildConfig")
        val _field = clazz.getField("LOG_URL")

        return _field.get(null) as String
    }

}