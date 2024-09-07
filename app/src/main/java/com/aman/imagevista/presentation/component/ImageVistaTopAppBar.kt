package com.aman.imagevista.presentation.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageVistaTopAppBar(
    modifier: Modifier = Modifier,
    title:String="Image Vista",
    onSearchClick:()->Unit={}
) {
    CenterAlignedTopAppBar(
        modifier = modifier,
        title = {
            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)){
                        append(title.split(" ").first())
                    }
                    withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.secondary)){
                        append(title.split(" ").last())
                    }
                }
            )
        },
        actions = {
            IconButton(onClick = { onSearchClick()}) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "search"
                )
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            scrolledContainerColor = MaterialTheme.colorScheme.background
        )
    )
}