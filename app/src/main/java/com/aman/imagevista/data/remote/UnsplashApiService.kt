package com.aman.imagevista.data.remote

import com.aman.imagevista.data.remote.dto.UnsplashImageDto
import com.aman.imagevista.data.util.Constants.API_KEY
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface UnsplashApiService {

    @Headers("Authorization: Client-ID $API_KEY")
    @GET("/photos")
    suspend fun getEditorialImages():List<UnsplashImageDto>

    @Headers("Authorization: Client-ID $API_KEY")
    @GET("/photos/{id}")
    suspend fun getImage(
        @Path("id") imageId:String
    ):UnsplashImageDto
}