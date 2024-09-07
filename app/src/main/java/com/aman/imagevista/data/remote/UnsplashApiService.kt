package com.aman.imagevista.data.remote

import com.aman.imagevista.data.remote.dto.UnsplashImageDto
import com.aman.imagevista.data.util.Constants.API_KEY
import retrofit2.http.GET
import retrofit2.http.Headers

interface UnsplashApiService {

    @Headers("Authorization: Client-ID $API_KEY")
    @GET("/photos")
    suspend fun getEditorialImages():List<UnsplashImageDto>
}