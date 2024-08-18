package com.jburch.youtubevideofinder.usecases.main

import android.content.Context
import android.content.Intent
import com.jburch.youtubevideofinder.usecases.base.BaseActivityRouter

class MainRouter: BaseActivityRouter {
    override fun intent(activity: Context): Intent = Intent(activity, MainActivity::class.java)

}