package com.aman.imagevista

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.aman.imagevista.presentation.home_screen.HomeScreen
import com.aman.imagevista.presentation.home_screen.HomeViewModel
import com.aman.imagevista.presentation.theme.ImageVistaTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ImageVistaTheme {
                val scrollBehaviour = TopAppBarDefaults.enterAlwaysScrollBehavior()
                val viewModel = viewModel<HomeViewModel>()
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .nestedScroll(scrollBehaviour.nestedScrollConnection)
                ) {
                    HomeScreen(
                        scrollBehavior = scrollBehaviour,
                        images =viewModel.images,
                        onImageClick = {},
                        onSearchClick = {},
                        onFABClick = {}
                    )
                }


            }
        }
    }
}

