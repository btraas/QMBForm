package com.devrygreenhouses.qmb.rows.push.fragment

import android.app.Activity
import android.support.v4.app.Fragment
import com.devrygreenhouses.qmb.rows.push.CustomFragmentActivity
import com.devrygreenhouses.qmb.rows.push.PushHandler
import com.quemb.qmbform.R
import com.quemb.qmbform.descriptor.*
import java.io.Serializable


class FragmentPushHandler(oldActivity: Activity,
                          title: String,
                          val buildFragment: () -> Fragment)

    : PushHandler<CustomFragmentActivity>(oldActivity, title), Serializable {


    override fun onPresent(oldActivity: Activity, newActivity: CustomFragmentActivity) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun generate(activity: CustomFragmentActivity) {

        val newFragment = buildFragment()


        val transaction = activity.supportFragmentManager.beginTransaction()
        transaction.add(R.id.fragment, buildFragment())
        transaction.commit()


    }



}