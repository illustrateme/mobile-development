package com.dicoding.illustrateme.model

import com.google.gson.annotations.SerializedName

data class SignInInfo(
    @SerializedName("username")
    val username: String,

    @SerializedName("password")
    val password: String

)
