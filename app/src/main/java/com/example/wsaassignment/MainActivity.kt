package com.example.wsaassignment

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

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val nextScreen = { screenContent: SeriesResult ->
            redirectTo(screenContent)
        }
        setContent {
            WSAAssignmentTheme {
                // A surface container using the 'background' color from the theme

                val redirectTo by rememberUpdatedState(newValue = nextScreen)
                TrendingVerticalGrid(
                    viewModel = viewModel,
                    redirectTo = redirectTo,
                    lazyLoad = { viewModel.lazyLoadElements() })
            }
        }
    }
}

fun redirectTo(seriesResult: SeriesResult) {

}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    WSAAssignmentTheme {
        Greeting("Android")
    }
}