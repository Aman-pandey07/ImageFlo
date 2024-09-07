package com.aman.imagevista.presentation.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.aman.imagevista.presentation.favorites_screen.FavoritesScreen
import com.aman.imagevista.presentation.full_image_screen.FullImageScreen
import com.aman.imagevista.presentation.home_screen.HomeScreen
import com.aman.imagevista.presentation.home_screen.HomeViewModel
import com.aman.imagevista.presentation.search_screen.SearchScreen
import okhttp3.Route


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavGraphSetup(
    navController: NavHostController,
    scrollBehaviour:TopAppBarScrollBehavior
) {
    NavHost(
        navController =navController,
        startDestination = Routes.HomeScreen
    ){
        composable<Routes.HomeScreen> {
            val viewModel = viewModel<HomeViewModel>()
            HomeScreen(
                scrollBehavior = scrollBehaviour,
                images =viewModel.images,
                onImageClick = {imageId->
                    navController.navigate(Routes.FullImageScreen(imageId))
                },
                onSearchClick = { navController.navigate(Routes.SearchScreen) },
                onFABClick = {navController.navigate(Routes.FavoritesScreen)}
            )
        }
        composable<Routes.SearchScreen> {
            SearchScreen(onBackClick = { navController.navigateUp() })
        }
        composable<Routes.FavoritesScreen> {
            FavoritesScreen(onBackClick = { navController.navigateUp() })
        }
        composable<Routes.FullImageScreen> {backStackEntry->
            val imageId = backStackEntry.toRoute<Routes.FullImageScreen>().imageId
            FullImageScreen(
                imageId =imageId ,
                onBackClick = { navController.navigateUp() }
            )
        }
        composable<Routes.ProfileScreen> {

        }
    }
}