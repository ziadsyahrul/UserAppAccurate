package com.ziad.userappaccurate.data.remote.api

import com.ziad.userappaccurate.data.remote.dto.AddUserRequest
import com.ziad.userappaccurate.data.remote.dto.CityDto
import com.ziad.userappaccurate.data.remote.dto.UserDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @GET("user")
    suspend fun getUsers(): List<UserDto>

    @POST("user")
    suspend fun addUser(@Body request: AddUserRequest): UserDto

    @GET("city")
    suspend fun getCities(): List<CityDto>
}