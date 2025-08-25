package com.thamaneya.androidchallenge.core.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest

/**
 * Async image composable with placeholder and error handling
 */
@Composable
fun ImageAsync(
    imageUrl: String?,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    shape: Shape? = null,
    contentScale: ContentScale = ContentScale.Crop,
    placeholderSize: Dp = 48.dp
) {
    val imageModifier = if (shape != null) {
        modifier.clip(shape)
    } else {
        modifier
    }
    
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .crossfade(true)
            .build(),
        contentDescription = contentDescription,
        modifier = imageModifier,
        contentScale = contentScale,
        onLoading = { painterState ->
//            Box(
//                modifier = Modifier.fillMaxSize(),
//                contentAlignment = Alignment.Center
//            ) {
//                CircularProgressIndicator(
//                    modifier = Modifier.size(placeholderSize),
//                    color = MaterialTheme.colorScheme.primary
//                )
//            }
        },
        onError = { painterState ->
//            Box(
//                modifier = Modifier.fillMaxSize(),
//                contentAlignment = Alignment.Center
//            ) {
//                Icon(
//                    imageVector = Icons.Default.Build,
//                    contentDescription = null,
//                    modifier = Modifier.size(placeholderSize),
//                    tint = MaterialTheme.colorScheme.onSurfaceVariant
//                )
//            }
        }
    )
}
