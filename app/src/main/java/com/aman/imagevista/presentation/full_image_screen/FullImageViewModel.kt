package com.aman.imagevista.presentation.full_image_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.aman.imagevista.domain.model.UnsplashImage
import com.aman.imagevista.domain.repository.Downloader
import com.aman.imagevista.domain.repository.ImageRepository
import com.aman.imagevista.presentation.navigation.Routes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.aman.imagevista.presentation.util.SnackbarEvent
import kotlinx.coroutines.flow.receiveAsFlow
import java.net.UnknownHostException

@HiltViewModel
class FullImageViewModel @Inject constructor(
    private val repository: ImageRepository,
    private val downloader: Downloader,
    savedStateHandle: SavedStateHandle
): ViewModel() {
    private val imageId = savedStateHandle.toRoute<Routes.FullImageScreen>().imageId

    private val _snackbarEvent = Channel<SnackbarEvent>()
    val snackbarEvent = _snackbarEvent.receiveAsFlow()


    var image: UnsplashImage? by mutableStateOf(null)
        private set

    init {
        getImage()
    }

    private fun getImage(){
        viewModelScope.launch {
            try {
                val result = repository.getImage(imageId)
                image=result
            }catch (e:UnknownHostException){
                _snackbarEvent.send(
                    SnackbarEvent(message = "No Internet Connection.Please check your internet connection")
                )
            }catch (e:Exception){
                _snackbarEvent.send(
                    SnackbarEvent(message = "Something went wrong: ${e.message}")
                )
            }
        }
    }

    fun downloadImage(url: String, title: String?) {
        viewModelScope.launch {
            try {
                downloader.downloadFile(url,title)
            } catch (e: Exception){
                _snackbarEvent.send(
                    SnackbarEvent(message = "Something went wrong: ${e.message}")
                )
            }
        }
    }

}