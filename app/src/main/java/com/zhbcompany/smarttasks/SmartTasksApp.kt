package com.zhbcompany.smarttasks

import android.app.Application
import com.zhbcompany.smarttasks.di.appModule
import com.zhbcompany.smarttasks.di.dataModule
import com.zhbcompany.smarttasks.di.domainModule
import com.zhbcompany.smarttasks.di.presentationModule
import com.zhbcompany.smarttasks.logger.CustomLogTree
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class SmartTasksApp : Application() {
    override fun onCreate() {
        super.onCreate()

        plantTimber()
        startKoinInApp()
    }

    private fun plantTimber() {
        Timber.plant(CustomLogTree.instance())
    }

    /**
     * Initializes Koin dependency injection framework in the application.
     *
     * This function is responsible for setting up the Koin modules that provide the necessary dependencies
     * for the application. It uses the [androidContext] function to provide the Android application context
     * and the [modules] function to define the list of Koin modules.
     *
     * @return Unit (does not return any value)
     */
    private fun startKoinInApp() {
        startKoin {
            androidContext(this@SmartTasksApp)
            modules(
                listOf(
                    appModule,
                    dataModule,
                    domainModule,
                    presentationModule
                )
            )
        }
    }
}