package com.zhbcompany.smarttasks.data.remote

import com.zhbcompany.smarttasks.data.remote.dto.TaskResponse
import retrofit2.http.GET

interface TaskApiService {
    @GET("/")
    suspend fun fetchTasks(): TaskResponse
}