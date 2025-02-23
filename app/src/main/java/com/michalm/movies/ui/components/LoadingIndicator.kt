package com.michalm.movies.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun LoadingIndicator(isLoading: Boolean) {
    val progressHeight = 4.dp

    Box(modifier = Modifier
        .fillMaxWidth()
        .height(progressHeight)
    ) {
        if (isLoading) {
            LinearProgressIndicator(modifier = Modifier.fillMaxSize())
        }
    }
}