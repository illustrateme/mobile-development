package com.dicoding.illustrateme.model

import com.google.gson.annotations.SerializedName

data class GetUserInfoResponse(

	@field:SerializedName("data")
	val data: DataUser,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: String
)

data class DataUser(

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("posts")
	val posts: List<PostsItemUser>,

	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("username")
	val username: String
)

data class PostsItemUser(

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("designStyleId")
	val designStyleId: Int,

	@field:SerializedName("imageUrl")
	val imageUrl: String,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("priceStartFrom")
	val priceStartFrom: Int,

	@field:SerializedName("designTypeId")
	val designTypeId: Int,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("authorId")
	val authorId: String,

	@field:SerializedName("updatedAt")
	val updatedAt: String
)
