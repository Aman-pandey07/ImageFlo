package com.aman.imagevista.presentation.home_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aman.imagevista.data.di.AppModule
import com.aman.imagevista.data.mapper.toDomainModelList
import com.aman.imagevista.domain.model.UnsplashImage
import kotlinx.coroutines.launch

class HomeViewModel:ViewModel() {
    var images:List<UnsplashImage> by mutableStateOf(emptyList())
        private set

    init {
        getImages()
    }

    private fun getImages(){
        viewModelScope.launch {
            val result = AppModule.retrofitService.getEditorialImages()
            images = result.toDomainModelList()
        }
    }
}