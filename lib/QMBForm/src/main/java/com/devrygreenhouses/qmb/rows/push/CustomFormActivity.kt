package com.devrygreenhouses.qmb.rows.push

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.ListView
import com.devrygreenhouses.qmb.rows.push.PushHandler
import com.devrygreenhouses.qmb.rows.push.PushHandlerPointer
import com.devrygreenhouses.qmb.rows.push.nested.NestedPushHandler
import com.quemb.qmbform.R


class CustomFormActivity : AppCompatActivity() {

    var handler: NestedPushHandler<*>? = null

    private val TAG = "CustomFormActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_push)

        Log.d(TAG, "onCreate")

        handler = (intent.getSerializableExtra("handler") as PushHandlerPointer).retrieve() as NestedPushHandler<*>

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = handler?.title

        //Toast.makeText(this, "generating form", Toast.LENGTH_SHORT).show()

        handler?.newActivity = this
        handler?.generate(this)


    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
