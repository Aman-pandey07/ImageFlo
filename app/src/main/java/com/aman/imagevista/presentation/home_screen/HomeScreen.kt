package com.aman.imagevista.presentation.home_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.aman.imagevista.data.remote.dto.UnsplashImageDto
import com.aman.imagevista.domain.model.UnsplashImage
import com.aman.imagevista.presentation.component.ImageCard
import com.aman.imagevista.presentation.component.ImageVistaTopAppBar
import com.aman.imagevista.presentation.component.ImagesVerticalGrid

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    scrollBehavior: TopAppBarScrollBehavior,
    images:List<UnsplashImage>,
    onImageClick:(String)->Unit,
    onSearchClick:()->Unit,
) {
    Column {
        ImageVistaTopAppBar(
            scrollBehavior = scrollBehavior,
            onSearchClick = onSearchClick
        )
        ImagesVerticalGrid(
            images =images,
            onImageClick = onImageClick
        )
    }

}