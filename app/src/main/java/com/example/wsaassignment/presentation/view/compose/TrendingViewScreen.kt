package com.example.wsaassignment.presentation.view.compose

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.wsaassignment.data.model.SeriesResult
import com.example.wsaassignment.presentation.view.compose.common.RemoteImage
import com.example.wsaassignment.presentation.viewmodel.MainViewModel
import com.example.wsaassignment.ui.theme.labelSmall
import com.example.wsaassignment.ui.theme.titleLarge
import com.example.wsaassignment.util.Constants

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SetupTrendingItemComponent(
    seriesData: SeriesResult,
    redirectTo: (data: SeriesResult) -> Unit
) {
    Card(
        modifier = Modifier
            .aspectRatio(9f/16f)
            .padding(vertical = 10.dp, horizontal = 5.dp)
            .fillMaxWidth(),
        onClick = {
            redirectTo(seriesData)
        }, shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(6.dp),
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

        Text(
            text = seriesData.originalTitle.toString(),
            style = titleLarge,
            modifier = Modifier.padding(vertical = 5.dp).fillMaxWidth(),
            textAlign = TextAlign.Center,
            maxLines = 1
        )

        Text(
            text = seriesData.voteAverage.toString(),
            style = labelSmall,
            modifier = Modifier.padding(vertical = 2.dp).fillMaxWidth(),
            textAlign =  TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun TrendingVerticalGrid(
    cells: GridCells = GridCells.Fixed(2),
    state: LazyListState = rememberLazyListState(),
    contentPadding: PaddingValues = PaddingValues(0.dp),
    redirectTo: (data: SeriesResult) -> Unit,
    viewModel : MainViewModel,
    lazyLoad : () -> Unit
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
                        redirectTo = redirectTo
                    )
                }
            }
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