package com.aman.imageflo.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.aman.imageflo.data.local.ImageVistaDatabase
import com.aman.imageflo.data.mapper.toDomainModel
import com.aman.imageflo.data.mapper.toFavoriteImageEntity
import com.aman.imageflo.data.paging.EditorialFeedRemoteMediator
import com.aman.imageflo.data.paging.SearchPagingSource
import com.aman.imageflo.data.remote.UnsplashApiService
import com.aman.imageflo.data.util.Constants.ITEMS_PER_PAGE
import com.aman.imageflo.domain.model.UnsplashImage
import com.aman.imageflo.domain.repository.ImageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ImageRepositoryImpl(
    private val unsplashApi: UnsplashApiService,
    private val database: ImageVistaDatabase
):ImageRepository {

    private val favoriteImagesDao = database.favoriteImagesDao()
    private val editorialFeedDao = database.editorialFeedDao()

    @OptIn(ExperimentalPagingApi::class)
    override fun getEditorialFeedImages(): Flow<PagingData<UnsplashImage>> {
        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
            remoteMediator = EditorialFeedRemoteMediator(unsplashApi, database),
            pagingSourceFactory = { editorialFeedDao.getAllEditorialFeedImages() }
        )
            .flow
            .map { pagingData ->
                pagingData.map { it.toDomainModel() }
            }
    }


    override suspend fun getImage(imageId: String): UnsplashImage {
        return unsplashApi.getImage(imageId).toDomainModel()

    }

    override fun searchImage(query: String): Flow<PagingData<UnsplashImage>> {
        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
            pagingSourceFactory = { SearchPagingSource(query, unsplashApi) }
        ).flow
    }

    override fun getAllFavoriteImages(): Flow<PagingData<UnsplashImage>> {
        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
            pagingSourceFactory = { favoriteImagesDao.getAllFavoriteImages() }
        )
            .flow
            .map { pagingData ->
                pagingData.map { it.toDomainModel() }
            }
    }

    override suspend fun toggleFavoriteStatus(image: UnsplashImage) {
        val isFavorite = favoriteImagesDao.isImageFavorite(image.id)
        val favoriteImage = image.toFavoriteImageEntity()
        if (isFavorite) {
            favoriteImagesDao.deleteFavoriteImage(favoriteImage)
        } else {
            favoriteImagesDao.insertFavoriteImage(favoriteImage)
        }
    }

    override fun getFavoriteImageIds(): Flow<List<String>> {
        return favoriteImagesDao.getFavoriteImageIds()
    }
}