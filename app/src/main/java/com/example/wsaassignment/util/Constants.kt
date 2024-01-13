package com.example.wsaassignment.util

class Constants {
    companion object {
        const val SOMETHING_WENT_WRONG = "Something went wrong"


        //API END POINT
        const val BASE_URL = "https://api.themoviedb.org/3/"//"https://developers.themoviedb.org/3/"

        //IMAGE_URL ENDPOINT
        const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/original/"

        const val API_ENDPOINT_TRENDING = "trending/all/{${Constants.PATH_TIME_WINDOW}}"
        const val API_ENDPOINT_SEARCH = "search/tv"
        const val API_ENDPOINT_SIMILAR_SHOWS = "tv/{${Constants.QUERY_PARAM_SERIES_ID}}"

        //Query Parameters
        const val QUERY_PARAM_API_KEY = "api_key"
        const val QUERY_PARAM_LANGUAGE = "language"

        const val PATH_TIME_WINDOW = "TIME_WINDOW"

        const val QUERY_PARAM_INCLUDE_ADULT = "include_adult"
        const val QUERY_PARAM_FIRST_AIR_DATE = "first_air_date_year"
        const val QUERY_PARAM_SEARCH_QUERY = "query"
        const val QUERY_PARAM_PAGE = "page"
        const val QUERY_PARAM_YEAR = "year"

        const val QUERY_PARAM_SERIES_ID = "series_id"

        //Headers
        const val HEADER_PARAM_ACCEPT = "Accept"
        const val HEADER_APPLICATION_JSON = "application/json"

        const val API_KEY = "78d78ade0e7afe76f022364799ac0be3"
        const val API_READ_ACCESS_TOKEN =
            "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI3OGQ3OGFkZTBlN2FmZTc2ZjAyMjM2NDc5OWFjMGJlMyIsInN1YiI6IjY1YTAxN2Q2YmVmYjA5MDEyOTg0NmM4OCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.SJC8J-lIn-pe4OhdsTBo4Bhy_wMCCn1YyKnvzb6EyNM"

        const val DEFAULT_LANGUAGE = "en-US"
        const val BLANK = ""

        const val CONNECTION_TIME_OUT: Long = 30
        const val WRITE_TIME_OUT: Long = 30
        const val READ_TIME_OUT: Long = 30
    }

    enum class TimeWindow(var timeWindow: String) {
        DAY("day"), WEEK("week")
    }
}