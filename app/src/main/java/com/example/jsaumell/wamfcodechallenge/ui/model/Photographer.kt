package com.example.jsaumell.wamfcodechallenge.ui.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Photographer(
    val id: Int,
    val name: String?,
    val username: String?,
    val city: String?,
    val zipcode: String?,
    val phone: String?,
    val website: String?,
    val catchphrase: String?
) : Parcelable