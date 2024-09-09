package com.aman.imagevista.presentation.full_image_screen

import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.animateZoomBy
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.aman.imagevista.R
import com.aman.imagevista.domain.model.UnsplashImage
import com.aman.imagevista.presentation.component.FullImageViewTopBar
import com.aman.imagevista.presentation.component.ImageVistaLoadingBar
import com.aman.imagevista.presentation.util.rememberWindowInsetsController
import com.aman.imagevista.presentation.util.toggleStatusBars
import kotlinx.coroutines.launch


import kotlin.math.max


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FullImageScreen(
    image:UnsplashImage?,
    onBackClick:()->Unit,
    onPhotographerImgClick:(String)->Unit,
) {
    val scope = rememberCoroutineScope()
    var showBars by rememberSaveable { mutableStateOf(false) }
    val windowInsertsController = rememberWindowInsetsController()
    
    LaunchedEffect(key1 = Unit) {
        windowInsertsController.toggleStatusBars(show = showBars)
    }
    BackHandler(enabled = !showBars ) {
        windowInsertsController.toggleStatusBars(show = true)
        onBackClick()
    }
    Box(
        modifier= Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        BoxWithConstraints(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        )
        {
            var scale by remember {mutableFloatStateOf(1f) }
            var offset by remember {mutableStateOf(Offset.Zero) }
            val isImageZoomed:Boolean by remember{ derivedStateOf { scale!=1f }}
            val transformState = rememberTransformableState {zoomChange, offsetChange, _ ->
                scale = max(scale*zoomChange,1f)
                val maxX = (constraints.maxWidth * (scale - 1)) / 2
                val maxY =(constraints.maxHeight * (scale - 1)) / 2
                    offset = Offset(
                        x= (offset.x + offsetChange.x).coerceIn(-maxX,maxX),
                        y=( offset.y + offsetChange.y).coerceIn(-maxY,maxY)
                    )
            }
            var isLoading by remember { mutableStateOf(true) }
            var isError by remember { mutableStateOf(true) }
            val imageLoader = rememberAsyncImagePainter(
                model =image?.imageUrlRaw,
                onState = {imageState->
                    isLoading = imageState is AsyncImagePainter.State.Loading
                    isError = imageState is AsyncImagePainter.State.Error
                }
            )
            if (isLoading){
                ImageVistaLoadingBar()
            }
            Image(
                painter = if (isError.not()) imageLoader else painterResource(id = R.drawable.ic_error),
                contentDescription = null,
                modifier = Modifier
                    .transformable(transformState)
                    .combinedClickable(
                        onDoubleClick = {
                            if (isImageZoomed) {
                                scale = 1f
                                offset = Offset.Zero
                            } else {
                                scope.launch { transformState.animateZoomBy(zoomFactor = 3f) }
                            }
                        },
                        onClick = {
                            showBars = !showBars
                            windowInsertsController.toggleStatusBars(show = showBars)
                        },
                        indication = null,
                        interactionSource = remember {
                            MutableInteractionSource()
                        }

                    )
                    .graphicsLayer {
                        scaleX = scale
                        scaleY = scale
                        translationX = offset.x
                        translationY = offset.y
                    }
            )
        }
        FullImageViewTopBar(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopStart)
                .padding(horizontal = 5.dp, vertical = 10.dp),
            image = image,
            isVisible = showBars,
            onBackClick = { onBackClick()},
            onPhotographerImgClick = onPhotographerImgClick,
            onDownloadImgClick = {}
        )



    }
}