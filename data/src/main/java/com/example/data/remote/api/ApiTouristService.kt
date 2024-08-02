package com.example.data.remote.api

import com.example.data.remote.model.response.MountainsResponseItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface ApiTouristService {
    @GET("mountains/")
    suspend fun getMountains(): Response<ArrayList<MountainsResponseItem>>

    @GET("mountains/{id}")
    suspend fun getDetailMountain(@Path("id") id: String): Response<MountainsResponseItem>

}