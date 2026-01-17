package com.omniful.app.presentation.movies.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.omniful.app.R
import com.omniful.designsystem.theme.BodyType
import com.omniful.designsystem.theme.BodyUnderlineType
import com.omniful.designsystem.theme.LocalOMFSize
import com.omniful.designsystem.theme.LocalTypography

@Composable
fun ErrorState(errorMessage: String = stringResource(R.string.something_went_wrong), onRetry: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(LocalOMFSize.current.spacing.spacing6)
    ) {
        Text(
            text = errorMessage,
            style = LocalTypography.current.body.styles[BodyType.B02]!!.medium
        )

        Spacer(modifier = Modifier.height(LocalOMFSize.current.spacing.spacing3))

        Text(
            text = stringResource(R.string.tap_to_retry),
            modifier = Modifier.clickable(onClick = onRetry),
            style = LocalTypography.current.bodyUnderline.styles[BodyUnderlineType.U02]!!.medium
        )
    }
}