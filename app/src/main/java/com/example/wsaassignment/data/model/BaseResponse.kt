package com.example.wsaassignment.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BaseResponse(
    @SerializedName("status_code") var statusCode : Int,
    @SerializedName("status_message") var statusMessage: String,
    @SerializedName("success") var success : Boolean
)  : Parcelable