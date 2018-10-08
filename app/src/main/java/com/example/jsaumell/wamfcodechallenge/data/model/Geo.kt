package com.example.jsaumell.wamfcodechallenge.data.model

import com.google.gson.annotations.SerializedName

data class Geo(

    @field:SerializedName("lng")
    val lng: String? = null,

    @field:SerializedName("lat")
    val lat: String? = null
)