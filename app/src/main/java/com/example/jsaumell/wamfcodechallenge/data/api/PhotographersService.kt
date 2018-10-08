package com.example.jsaumell.wamfcodechallenge.data.api

import com.example.jsaumell.wamfcodechallenge.data.model.PhotoInfo
import com.example.jsaumell.wamfcodechallenge.data.model.ApiPhotographer
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface PhotographersService {
    @GET("users")
    fun getListOfPhotographers(): Call<List<ApiPhotographer>>

    @GET("albums/{photographer_id}/photos")
    fun getListOfPhotos(@Path("photographer_id") id: Int): Call<List<PhotoInfo>>
}