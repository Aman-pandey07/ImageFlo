package com.aman.imagevista

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.aman.imagevista.presentation.home_screen.HomeScreen
import com.aman.imagevista.presentation.home_screen.HomeViewModel
import com.aman.imagevista.presentation.theme.ImageVistaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ImageVistaTheme {
                val viewModel = viewModel<HomeViewModel>()
                HomeScreen(images =viewModel.images )
            }
        }
    }
}

