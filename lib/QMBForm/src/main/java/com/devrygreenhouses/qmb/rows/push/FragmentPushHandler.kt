package com.devrygreenhouses.qmb.rows.push

import android.app.Activity
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.content.ContextCompat
import android.util.Log
import android.widget.FrameLayout
import android.widget.ListView
import android.widget.TextView
import com.devrygreenhouses.qmb.ReflectionTools
import com.devrygreenhouses.qmb.rows.push.nested.FinishCell
import com.devrygreenhouses.qmb.rows.push.nested.NestedPushHandler
import com.devrygreenhouses.qmb.rows.push.nested.NestedPushRowDescriptor
import com.quemb.qmbform.FormManager
import com.quemb.qmbform.OnFormRowClickListener
import com.quemb.qmbform.R
import com.quemb.qmbform.descriptor.*
import com.quemb.qmbform.view.Cell
import kotlinx.android.synthetic.main.activity_custom_fragment.view.*
import java.io.Serializable


class FragmentPushHandler(oldActivity: Activity,
                          title: String,
                          val buildFragment: () -> Fragment,
                          valueChangedListener: OnFormRowValueChangedListener)

    : PushHandler<CustomFragmentActivity>(oldActivity, title, valueChangedListener), Serializable {


    override fun onPresent(oldActivity: Activity, newActivity: CustomFragmentActivity) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun generate(activity: CustomFragmentActivity) {
//        val frame = activity.findViewById<FrameLayout>(R.id.fragment)

        val transaction = activity.supportFragmentManager.beginTransaction()
        transaction.add(R.id.fragment, buildFragment())
        transaction.commit()

    }


}