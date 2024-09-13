package com.aman.imagevista.data.util

import com.aman.imagevista.BuildConfig

object Constants {

    const val IV_LOG_TAG = "ImageVista"
    const val API_KEY = BuildConfig.UNSPLASH_API_KEY
    const val BASE_URL = "https://api.unsplash.com/"
    const val ITEMS_PER_PAGE = 10
    const val FAVORITE_IMAGE_TABLE = "favorite_images_table"
    const val IMAGE_VISTA_DATABASE = "unsplash_images.db"
    const val UNSPLASH_IMAGE_TABLE = "images_table"
    const val REMOTE_KEYS_TABLE = "remote_keys_table"
}