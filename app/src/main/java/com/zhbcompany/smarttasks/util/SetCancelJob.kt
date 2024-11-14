package com.zhbcompany.smarttasks.util

import kotlinx.coroutines.Job
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class SetCancelJob : ReadWriteProperty<Any?, Job> {

    private var fieldRef: Job = Job()

    override fun getValue(thisRef: Any?, property: KProperty<*>): Job {
        return fieldRef
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: Job) {
        fieldRef.cancel()
        fieldRef = value
    }
}
