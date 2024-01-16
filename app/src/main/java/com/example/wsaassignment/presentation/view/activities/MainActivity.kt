package com.example.wsaassignment.presentation.view.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.wsaassignment.data.model.SeriesResult
import com.example.wsaassignment.presentation.view.compose.SearchBarUI
import com.example.wsaassignment.presentation.view.compose.TrendingVerticalGrid
import com.example.wsaassignment.presentation.viewmodel.MainViewModel
import com.example.wsaassignment.ui.theme.WSAAssignmentTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        renderUI()
    }

    private fun renderUI() {
        setContent {
            WSAAssignmentTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    if (viewModel.isNetworkConnected(this@MainActivity)) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            SearchBarUI(viewModel = viewModel, redirectTo = { redirectTo(it) })
                            TrendingVerticalGrid(
                                viewModel = viewModel,
                                redirectTo = { redirectTo(it) },
                                isSearch = false)
                        }
                    }
                }
                
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchFavoritesMovieList()
    }

    private fun redirectTo(seriesResult: SeriesResult) {
        val intent = Intent(this@MainActivity, DetailsActivity::class.java)
        intent.putExtras(DetailsActivity.getExtras(seriesResult))
        startActivity(intent)
    }
}
