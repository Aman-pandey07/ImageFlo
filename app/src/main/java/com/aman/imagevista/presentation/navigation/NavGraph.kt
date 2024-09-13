package com.aman.imagevista.presentation.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.aman.imagevista.presentation.favorites_screen.FavoritesScreen
import com.aman.imagevista.presentation.full_image_screen.FullImageScreen
import com.aman.imagevista.presentation.full_image_screen.FullImageViewModel
import com.aman.imagevista.presentation.home_screen.HomeScreen
import com.aman.imagevista.presentation.home_screen.HomeViewModel
import com.aman.imagevista.presentation.profile_screen.ProfileScreen
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
            val viewModel:HomeViewModel = hiltViewModel()
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
        composable<Routes.FullImageScreen> {
            val fullImageViewModel: FullImageViewModel = hiltViewModel()
            FullImageScreen(
                image =fullImageViewModel.image,
                onBackClick = { navController.navigateUp() },
                onPhotographerNameClick = {profileLink->
                    navController.navigate(Routes.ProfileScreen(profileLink))
                }
            )
        }
        composable<Routes.ProfileScreen> { backStackEntry->
            val profileLink = backStackEntry.toRoute<Routes.ProfileScreen>().profileLink
            ProfileScreen(
                profileLink = profileLink,
                onBackClick = { navController.navigateUp() }
            )
        }
    }
}