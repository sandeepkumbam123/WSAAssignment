package com.example.wsaassignment.presentation.view.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.wsaassignment.R
import com.example.wsaassignment.dao.entities.FavoriteMovieData
import com.example.wsaassignment.data.model.SeriesResult
import com.example.wsaassignment.presentation.view.compose.common.RemoteImage
import com.example.wsaassignment.presentation.viewmodel.MainViewModel
import com.example.wsaassignment.ui.theme.labelSmall
import com.example.wsaassignment.util.Constants

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SetupTrendingItemComponent(
    seriesData: SeriesResult,
    redirectTo: (data: SeriesResult) -> Unit,
    viewModel: MainViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize()
            .padding(horizontal = 5.dp)
            .padding(top = 5.dp)
    ) {
        Box(contentAlignment = Alignment.TopEnd) {


            Card(
                modifier = Modifier
                    .aspectRatio(9f / 16f)
                    .fillMaxWidth()
                    .wrapContentSize(),
                onClick = {
                    redirectTo(seriesData)
                }, shape = RoundedCornerShape(10.dp),
                elevation = CardDefaults.cardElevation(2.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White,
                    contentColor = Color.White
                )
            ) {
                RemoteImage(
                    url = Constants.IMAGE_BASE_URL + seriesData.posterPath,
                    modifier = Modifier
                        .wrapContentHeight()
                        .fillMaxWidth(),
                    contentScale = ContentScale.Fit,
                    contentDescription = "Trending"
                )
            }

            FavoriteButton(modifier = Modifier
                .padding(2.dp)
                .padding(top = 15.dp)
                .height(8.dp), viewModel =  viewModel, seriesData =  seriesData)
        }
        val nameSeries = seriesData.originalName ?: seriesData.name ?: seriesData.originalTitle
        ?: seriesData.title ?: Constants.BLANK

        Text(
            text = nameSeries,
            style = labelSmall,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 5.dp),
            textAlign = TextAlign.Start,
            maxLines = 1
        )
        seriesData.voteAverage?.let {
            RatingView(rating = it)
        }
    }
}

@Composable
fun TrendingVerticalGrid(
    cells: GridCells = GridCells.Fixed(3),
    state: LazyListState = rememberLazyListState(),
    contentPadding: PaddingValues = PaddingValues(5.dp),
    redirectTo: (data: SeriesResult) -> Unit,
    viewModel: MainViewModel,
    lazyLoad: () -> Unit
) {
    val trendingData by viewModel.trendingMovieList.collectAsState()
    trendingData?.let {
        LazyVerticalGrid(columns = cells, contentPadding = contentPadding) {
            items(it.results.size) { item ->
                run {
                    //Lazy load call
                    if (item == it.results.size - 3) {
//                        lazyLoad()
                    }
                    SetupTrendingItemComponent(
                        seriesData = it.results[item],
                        redirectTo = redirectTo,
                        viewModel = viewModel
                        )
                }
            }
        }
    }
}

@Composable
fun RatingView(rating: Double) {
    Row(
        modifier = Modifier
            .wrapContentSize()
            .padding(horizontal = 5.dp)
    ) {
        val imageModifier = Modifier
            .height(height = 10.dp)
        for (index in 0 until 5) {
            Image(
                painter = painterResource(id = R.drawable.ic_rating_drawable),
                contentDescription = "Rating",
                contentScale = ContentScale.Fit,
                modifier = imageModifier,
                colorFilter = ColorFilter.tint(if (index > rating / 2) Color.Gray else Color.Red)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit
) {
    SearchBar(
        query = searchQuery,
        onQueryChange = onSearchQueryChange,
        onSearch = {},
        placeholder = {
            Text(text = "Search movies")
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                tint = MaterialTheme.colorScheme.onSurface,
                contentDescription = null
            )
        },
        trailingIcon = {},
        content = {},
        active = true,
        onActiveChange = {},
        tonalElevation = 0.dp
    )
}

@Composable
fun FavoriteButton(
    modifier: Modifier = Modifier,
    color: Color = Color.White,
    viewModel: MainViewModel,
    seriesData: SeriesResult
) {
    val favoriteMovies  by viewModel.favoriteMovieDataList.collectAsStateWithLifecycle()
    val isFavorite = favoriteMovies.find { it.seriesData.id == seriesData.id } != null
    IconToggleButton(
        checked = isFavorite,
        onCheckedChange = {

        }
    ) {
        Icon(
            tint = Color.Red,

            imageVector = if (isFavorite) {
                Icons.Filled.Favorite
            } else {
                Icons.Default.FavoriteBorder
            },
            contentDescription = null
        )
    }

}

//@ExperimentalMaterial3Api
//@Composable
//fun SearchBar(
//    query: String,
//    onQueryChange: (String) -> Unit,
//    onSearch: (String) -> Unit,
//    active: Boolean,
//    onActiveChange: (Boolean) -> Unit,
//    modifier: Modifier = Modifier,
//    enabled: Boolean = true,
//    placeholder: @Composable (() -> Unit)? = null,
//    leadingIcon: @Composable (() -> Unit)? = null,
//    trailingIcon: @Composable (() -> Unit)? = null,
//    shape: Shape = SearchBarDefaults.inputFieldShape,
//    colors: SearchBarColors = SearchBarDefaults.colors(),
//    tonalElevation: Dp = SearchBarDefaults.Elevation,
//    windowInsets: WindowInsets = SearchBarDefaults.windowInsets,
//    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
//    content: @Composable ColumnScope.() -> Unit,
//)