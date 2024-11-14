package com.zhbcompany.smarttasks.data.remote.dto

import com.google.gson.annotations.SerializedName

data class TaskRemote(
    @SerializedName("id")
    val id: String,

    @SerializedName("TargetDate")
    val targetDate: String? = null,

    @SerializedName("DueDate")
    val dueDate: String? = null,

    @SerializedName("Title")
    val title: String,

    @SerializedName("Description")
    val description: String,

    @SerializedName("Priority")
    val priority: Int
)

data class TaskResponse(
    val tasks: List<TaskRemote>
)