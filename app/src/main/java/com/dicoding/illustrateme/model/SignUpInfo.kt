package com.dicoding.illustrateme.model

import com.google.gson.annotations.SerializedName

data class SignUpInfo(
    @SerializedName("username")
    val username: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("password")
    val password: String
)
