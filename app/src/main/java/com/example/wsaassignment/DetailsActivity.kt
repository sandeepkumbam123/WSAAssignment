package com.example.wsaassignment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.wsaassignment.data.model.SeriesResult
import com.example.wsaassignment.presentation.view.compose.DetailsScreenCard
import com.example.wsaassignment.presentation.viewmodel.DetailsViewModel
import com.example.wsaassignment.ui.theme.WSAAssignmentTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsActivity : ComponentActivity() {

    private val viewModel:DetailsViewModel by viewModels<DetailsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WSAAssignmentTheme {
                DetailsScreenCard(click = {}, viewModel = viewModel)
            }
        }
    }
    companion object {
        @JvmStatic
        fun getExtras(seriesData: SeriesResult?): Bundle {
            val bundle = Bundle()
            seriesData?.let {
                bundle.putParcelable(DetailsViewModel.keyTrendingSeriesData, it)
            }
            return bundle
        }
    }

}