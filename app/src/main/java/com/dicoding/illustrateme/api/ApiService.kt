package com.dicoding.illustrateme.api

import com.dicoding.illustrateme.model.*
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @POST("register")
    fun signUp(
        @Body userData: SignUpInfo
    ): Call<AuthResponse>

    @POST("login")
    fun signIn(
        @Body userData: SignInInfo
    ): Call<AuthResponse>

    @GET("post/posts")
    fun getAllPosts(
        @Header("Authorization") token: String
    ): Call<PostResponse>

    @GET("user/{username}")
    fun getUserInfo(
        @Header("Authorization") token: String,
        @Path("username") username: String
    ): Call<GetUserInfoResponse>

    @GET("post/search")
    fun getSearchPost(
        @Header("Authorization") token: String,
        @Query("query") query: String
    ): Call<SearchResponse>

}