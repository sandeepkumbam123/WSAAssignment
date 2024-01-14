package com.example.wsaassignment.data.model

import android.os.Parcelable
import com.example.wsaassignment.util.Constants
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TrendingData(
    @SerializedName("page") var page: Int? = null,
    @SerializedName("results") var results: ArrayList<SeriesResult> = arrayListOf(),
    @SerializedName("total_pages") var totalPages: Int? = null,
    @SerializedName("total_results") var totalResults: Int? = null

) : Parcelable

@Parcelize
data class SeriesResult(
    @SerializedName("adult") var adult: Boolean? = false,
    @SerializedName("backdrop_path") var backdropPath: String? = Constants.BLANK,
    @SerializedName("id") var id: Int?,
    @SerializedName("title") var title: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("original_language") var originalLanguage: String? = null,
    @SerializedName("original_title") var originalTitle: String? = null,
    @SerializedName("original_name") var originalName: String? = null,
    @SerializedName("overview") var overview: String? = null,
    @SerializedName("poster_path") var posterPath: String? = null,
    @SerializedName("media_type") var mediaType: String? = null,
    @SerializedName("genre_ids") var genreIds: ArrayList<Int> = arrayListOf(),
    @SerializedName("popularity") var popularity: Double? = null,
    @SerializedName("release_date") var releaseDate: String? = null,
    @SerializedName("first_air_date") var firstAirDate: String? = null,
    @SerializedName("video") var video: Boolean? = null,
    @SerializedName("vote_average") var voteAverage: Double? = null,
    @SerializedName("vote_count") var voteCount: Int? = null

) : Parcelable