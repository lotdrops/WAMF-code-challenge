package com.example.jsaumell.wamfcodechallenge.data.model

import com.google.gson.annotations.SerializedName

data class ApiPhotographer(

    @field:SerializedName("website")
val website: String? = null,

    @field:SerializedName("address")
val address: Address? = null,

    @field:SerializedName("phone")
val phone: String? = null,

    @field:SerializedName("name")
val name: String? = null,

    @field:SerializedName("company")
val company: Company? = null,

    @field:SerializedName("id")
val id: Int,

    @field:SerializedName("email")
val email: String? = null,

    @field:SerializedName("username")
val username: String? = null
)