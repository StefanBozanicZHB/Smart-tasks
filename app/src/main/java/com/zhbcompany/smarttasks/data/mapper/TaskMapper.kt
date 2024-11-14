package com.zhbcompany.smarttasks.data.mapper

import com.zhbcompany.smarttasks.data.local.dto.TaskLocal
import com.zhbcompany.smarttasks.data.remote.dto.TaskRemote
import com.zhbcompany.smarttasks.util.DateUtil
import com.zhbcompany.smarttasks.domain.model.TaskDomain

fun TaskDomain.toLocalTask(): TaskLocal {
    return TaskLocal (
        id = id,
        title = title,
        description = description,
        dueDate = dueDate,
        targetDate = targetDate,
        priority = priority,
        status = status,
        comment = comment,
    )
}

fun TaskLocal.toDomainTask(): TaskDomain {
    return TaskDomain (
        id = id,
        title = title,
        description = description,
        dueDate = dueDate,
        targetDate = targetDate,
        priority = priority,
        status = status,
        comment = comment,
    )
}

fun TaskRemote.toDomainTask(): TaskDomain {
    return TaskDomain (
        id = id,
        title = title,
        description = description,
        dueDate = DateUtil.toDate(dueDate) ,
        targetDate = DateUtil.toDate(targetDate),
        priority = priority,
    )
}

fun TaskRemote.toLocalTask(): TaskLocal {
    return TaskLocal (
        id = id,
        title = title,
        description = description,
        dueDate = DateUtil.toDate(dueDate) ,
        targetDate = DateUtil.toDate(targetDate),
        priority = priority,
    )
}

fun List<TaskDomain>.toLocalTaskItems(): List<TaskLocal> = this.map { it.toLocalTask() }
fun List<TaskLocal>.toDomainTaskItemsFromLocal(): List<TaskDomain> = this.map { it.toDomainTask() }
fun List<TaskRemote>.toDomainTaskItemsFromRemote(): List<TaskDomain> = this.map { it.toDomainTask() }
fun List<TaskRemote>.toLocalTaskItemsFromRemote(): List<TaskLocal> = this.map { it.toLocalTask() }