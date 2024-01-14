package com.example.wsaassignment.presentation.view.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.example.wsaassignment.presentation.view.compose.common.RemoteImage
import com.example.wsaassignment.presentation.viewmodel.DetailsViewModel
import com.example.wsaassignment.ui.theme.labelSmall
import com.example.wsaassignment.ui.theme.titleLarge
import com.example.wsaassignment.util.Constants

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreenCard(click: (Int) -> Unit, viewModel: DetailsViewModel) {
    val seriesInfo by viewModel.seriesResultData.collectAsState()
    seriesInfo?.let {
        Column(
            modifier = Modifier
                .padding(vertical = 5.dp, horizontal = 5.dp)
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 9f),
                onClick = { it.id?.let(click) },
                shape = RoundedCornerShape(10.dp),
                colors = CardDefaults.cardColors(
                    Color.White, Color.White
                ),
                elevation = CardDefaults.cardElevation(6.dp)
            ) {
                RemoteImage(
                    url = Constants.IMAGE_BASE_URL + it.backdropPath,
                    modifier = Modifier.fillMaxWidth(),
                    contentScale = ContentScale.Fit
                )
            }

            Text(
                text = it.originalTitle ?: (it.title ?: "Series"),
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(5.dp, 5.dp),
                style = titleLarge
            )

            Text(
                text = it.overview ?: "Empty Description", modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth()
                    .padding(5.dp, 5.dp), style = labelSmall
            )
            SimilarSeriesList(viewModel = viewModel)
        }
    }
}

@Composable
fun SimilarSeriesList(viewModel: DetailsViewModel) {
    val seriesInfo by viewModel.similarSeriesInfo.collectAsState()
    seriesInfo?.let {
        LazyRow(
            modifier = Modifier.fillMaxWidth().wrapContentHeight()
        ) {
            items(it.results) {
                Card(
                    modifier = Modifier
                        .wrapContentHeight()
                        .wrapContentSize()
                        .padding(5.dp,5.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = CardDefaults.cardColors(
                        Color.White, Color.White
                    ),
                    elevation = CardDefaults.cardElevation(6.dp)
                ) {
                    RemoteImage(
                        url = Constants.IMAGE_BASE_URL + it.posterPath,
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(9f / 16f)
                            .wrapContentHeight(),
                        contentScale = ContentScale.FillBounds
                    )
                }
            }
        }
    }
}