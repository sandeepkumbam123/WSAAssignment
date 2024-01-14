package com.example.wsaassignment.presentation.view.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.wsaassignment.R
import com.example.wsaassignment.presentation.view.compose.common.RemoteImage
import com.example.wsaassignment.presentation.viewmodel.DetailsViewModel
import com.example.wsaassignment.ui.theme.labelSmall
import com.example.wsaassignment.ui.theme.titleLarge
import com.example.wsaassignment.util.Constants

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreenCard(click: (Int) -> Unit, viewModel: DetailsViewModel) {
    val seriesInfo = viewModel.seriesResultData
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
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                (it.title ?: (it.originalTitle ?: (it.name ?: it.originalName)))?.let { it1 ->
                    Text(
                        text = it1,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .width(300.dp)
                            .wrapContentHeight()
                            .padding(5.dp, 5.dp),
                        style = titleLarge
                    )
                }
                val isSeriesFavoriteinDetails = viewModel.isSeriesFavorited.collectAsStateWithLifecycle()
                val imageModifier = Modifier
                    .size(25.dp)
                    .width(15.dp)
                    .wrapContentSize()
                    .padding(horizontal = 5.dp, vertical = 5.dp)
                    .clickable {
                        viewModel.fetchFavoriteMovies()
                    }
                Image(
                    painter = painterResource(id = R.drawable.ic_favorite),
                    contentDescription = "Favorite",
                    contentScale = ContentScale.Fit,
                    modifier = imageModifier,
                    colorFilter = ColorFilter.tint(if (isSeriesFavoriteinDetails.value) Color.Red else Color.LightGray),
                    alignment = Alignment.Center
                )
            }

            Text(
                text = it.overview ?: "Empty Description", modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth()
                    .padding(5.dp, 5.dp), style = labelSmall
            )
            Row {
                it.voteAverage?.let {
                    RatingView(rating = it)
                }
                it.voteCount?.let {
                    Text(
                        text = "($it)",
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(horizontal = 2.dp),
                        style = labelSmall,
                        textAlign = TextAlign.Center
                    )
                }
            }
            val releaseData = it.releaseDate ?: it.firstAirDate ?: Constants.BLANK
            if (releaseData.isNotEmpty()) {
                Text(text = "Release Info",
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(horizontal = 5.dp)
                        .padding(top = 10.dp),
                    style = titleLarge
                )
                Text(text = releaseData,
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(horizontal = 5.dp, vertical = 5.dp),
                    style = labelSmall
                )
            }
            SimilarSeriesList(viewModel = viewModel)
        }
    }
}

@Composable
fun SimilarSeriesList(viewModel: DetailsViewModel) {
    val seriesInfo by viewModel.similarSeriesInfo.collectAsState()
    seriesInfo?.let {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(10.dp)
        )
        Text(
            text = "Similar shows",
            modifier = Modifier
                .padding(5.dp, 2.dp)
                .wrapContentSize(),
            style = titleLarge,
            maxLines = 1
        )
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(vertical = 5.dp)
        ) {
            items(it.results) {
                Card(
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(5.dp, 5.dp),
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
                            .width(width = 75.dp)
                            .aspectRatio(9f / 16f)
                            .wrapContentHeight(),
                        contentScale = ContentScale.FillBounds
                    )
                }
            }
        }
    }
}