package com.aman.imagevista.data.repository

import com.aman.imagevista.data.mapper.toDomainModel
import com.aman.imagevista.data.mapper.toDomainModelList
import com.aman.imagevista.data.remote.UnsplashApiService
import com.aman.imagevista.domain.model.UnsplashImage
import com.aman.imagevista.domain.repository.ImageRepository

class ImageRepositoryImpl(
    private val unsplashApi: UnsplashApiService
):ImageRepository {
    override suspend fun getEditorialFeedImages(): List<UnsplashImage> {
        return unsplashApi.getEditorialImages().toDomainModelList()
    }

    override suspend fun getImage(imageId: String): UnsplashImage {
        return unsplashApi.getImage(imageId).toDomainModel()

    }
}