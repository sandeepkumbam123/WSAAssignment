package com.example.wsaassignment

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.wsaassignment.data.model.SeriesResult
import com.example.wsaassignment.presentation.view.compose.TrendingVerticalGrid
import com.example.wsaassignment.presentation.viewmodel.MainViewModel
import com.example.wsaassignment.ui.theme.WSAAssignmentTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            WSAAssignmentTheme {
                // A surface container using the 'background' color from the theme

                TrendingVerticalGrid(
                    viewModel = viewModel,
                    redirectTo = { redirectTo(it) },
                    lazyLoad = { viewModel.lazyLoadElements() })
            }
        }
    }


    private fun redirectTo(seriesResult: SeriesResult) {
        val intent = Intent(this@MainActivity, DetailsActivity::class.java)
        intent.putExtras(DetailsActivity.getExtras(seriesResult))
        startActivity(intent)
    }
}
