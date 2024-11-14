package com.zhbcompany.smarttasks.logger

import android.util.Log
import com.zhbcompany.smarttasks.BuildConfig
import timber.log.Timber

/**
 * Log for debug build or log priority >= Warn priority level logs
 * see [Log.WARN]
 */
class CustomLogTree : Timber.DebugTree() {

    override fun isLoggable(tag: String?, priority: Int): Boolean {
        return BuildConfig.DEBUG || priority >= Log.WARN
    }

    companion object {
        @JvmStatic
        fun instance(): Timber.Tree {
            return CustomLogTree()
        }
    }
}