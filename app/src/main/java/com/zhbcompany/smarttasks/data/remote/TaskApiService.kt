package com.zhbcompany.smarttasks.data.remote

import com.zhbcompany.smarttasks.data.remote.dto.TaskResponse
import retrofit2.http.GET

/**
 * This interface defines the API service for fetching tasks.
 *
 * @author Stefan Bozanic
 * @since 1.0.0
 */
interface TaskApiService {

    /**
     * Fetches a list of tasks from the remote server.
     *
     * @return A [TaskResponse] object containing the fetched tasks.
     *
     * @throws Exception If an error occurs during the network request or parsing of the response.
     */
    @GET("/")
    suspend fun fetchTasks(): TaskResponse
}