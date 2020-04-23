package com.example.task2

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface HabitApi {
    @GET("habit")
    suspend fun getAll(): List<Habit>

    @PUT("habit")
    suspend fun update(@Body habit: Habit): ResponseBody

    @PUT("habit")
    suspend fun add(@Body habit: SimpleHabit): ResponseBody

    @HTTP(method="DELETE", path="habit", hasBody=true)
    suspend fun delete(@Body uid: String)
}