package com.aman.imagevista.domain.repository

import com.aman.imagevista.domain.model.UnsplashImage

interface ImageRepository {

    suspend fun getEditorialFeedImages(): List<UnsplashImage>
}