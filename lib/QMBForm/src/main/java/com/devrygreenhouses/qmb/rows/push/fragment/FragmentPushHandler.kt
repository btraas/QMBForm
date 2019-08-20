package com.devrygreenhouses.qmb.rows.push.fragment

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.devrygreenhouses.qmb.rows.push.CustomFragmentActivity
import com.devrygreenhouses.qmb.rows.push.PushHandler
import com.quemb.qmbform.R
import com.quemb.qmbform.descriptor.*
import java.io.Serializable


class FragmentPushHandler(oldActivity: Activity,
                          title: String,
                          val canPresentFragment: () -> Boolean,
                          val buildFragment: (newActivity: AppCompatActivity) -> Fragment)

    : PushHandler<CustomFragmentActivity>(oldActivity, title), Serializable {
    override fun onFormRowClick(itemDescriptor: FormItemDescriptor?) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    override fun onPresent(oldActivity: Activity, newActivity: CustomFragmentActivity) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun generate(activity: CustomFragmentActivity) {

        val newFragment = buildFragment(activity)


        val transaction = activity.supportFragmentManager.beginTransaction()
        transaction.add(R.id.fragment, newFragment)
        transaction.commit()


    }

    override fun canPresent(): Boolean {
        return canPresentFragment()
    }



}