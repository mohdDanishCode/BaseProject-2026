package com.omniful.app.presentation.movies.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.omniful.designsystem.theme.LocalOMFSize

@Composable
fun LoadingState() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(LocalOMFSize.current.spacing.spacing6),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}
