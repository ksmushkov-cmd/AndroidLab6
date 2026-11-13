package com.example.artspaceapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.artspaceapp.ui.theme.ArtSpaceAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ArtSpaceAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ArtSpaceAppScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun ArtSpaceAppScreen(
    modifier: Modifier = Modifier,
    viewModel: ArtSpaceViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    // Выбор картины по индексу через when (замечание преподавателя)
    val currentArtwork = when (uiState.currentArtworkIndex) {
        0 -> artworks[0]
        1 -> artworks[1]
        2 -> artworks[2]
        else -> artworks[0]
    }

    BoxWithConstraints(modifier = modifier.fillMaxSize()) {
        // Поддержка широкого формата экрана
        val isWideScreen = maxWidth > 600.dp

        if (isWideScreen) {
            // Горизонтальная раскладка (планшет / ландшафт)
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(currentArtwork.imageRes),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .weight(1f)
                        .height(400.dp)
                        .clip(RoundedCornerShape(8.dp))
                )

                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    ArtworkInfo(
                        titleRes = currentArtwork.titleRes,
                        artistRes = currentArtwork.artistRes
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    NavigationButtons(
                        canGoPrevious = uiState.currentArtworkIndex > 0,
                        canGoNext = uiState.currentArtworkIndex < uiState.totalArtworks - 1,
                        onPrevious = { viewModel.previousArtwork() },
                        onNext = { viewModel.nextArtwork() }
                    )
                }
            }
        } else {
            // Вертикальная раскладка (телефон)
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(currentArtwork.imageRes),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(400.dp)
                        .clip(RoundedCornerShape(8.dp))
                )

                Spacer(modifier = Modifier.height(24.dp))

                ArtworkInfo(
                    titleRes = currentArtwork.titleRes,
                    artistRes = currentArtwork.artistRes
                )

                Spacer(modifier = Modifier.height(32.dp))

                NavigationButtons(
                    canGoPrevious = uiState.currentArtworkIndex > 0,
                    canGoNext = uiState.currentArtworkIndex < uiState.totalArtworks - 1,
                    onPrevious = { viewModel.previousArtwork() },
                    onNext = { viewModel.nextArtwork() }
                )
            }
        }
    }
}

@Composable
private fun ArtworkInfo(titleRes: Int, artistRes: Int) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = stringResource(titleRes),
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = stringResource(artistRes),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
private fun NavigationButtons(
    canGoPrevious: Boolean,
    canGoNext: Boolean,
    onPrevious: () -> Unit,
    onNext: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Button(
            onClick = onPrevious,
            enabled = canGoPrevious,
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = stringResource(R.string.previous_artwork)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(stringResource(R.string.previous))
        }

        Button(
            onClick = onNext,
            enabled = canGoNext,
            modifier = Modifier
                .weight(1f)
                .padding(start = 8.dp)
        ) {
            Text(stringResource(R.string.next))
            Spacer(modifier = Modifier.width(4.dp))
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = stringResource(R.string.next_artwork)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ArtSpaceAppPreview() {
    ArtSpaceAppTheme {
        ArtSpaceAppScreen()
    }
}