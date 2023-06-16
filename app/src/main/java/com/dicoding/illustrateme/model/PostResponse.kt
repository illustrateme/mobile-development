package com.dicoding.illustrateme.model

import com.google.gson.annotations.SerializedName

data class PostResponse(

	@field:SerializedName("data")
	val data: DataPost ,

	@field:SerializedName("message")
	val message: String ,

	@field:SerializedName("status")
	val status: String 
)

data class DesignType(

	@field:SerializedName("name")
	val name: String 
)

data class DesignStyle(

	@field:SerializedName("name")
	val name: String
)

data class Author(

	@field:SerializedName("username")
	val username: String
)

data class PostsItem(

	@field:SerializedName("author")
	val author: Author ,

	@field:SerializedName("description")
	val description: Any? = null,

	@field:SerializedName("priceStartFrom")
	val priceStartFrom: Int ,

	@field:SerializedName("design_type")
	val designType: DesignType ,

	@field:SerializedName("title")
	val title: String ,

	@field:SerializedName("authorId")
	val authorId: String ,

	@field:SerializedName("design_style")
	val designStyle: DesignStyle ,

	@field:SerializedName("createdAt")
	val createdAt: String ,

	@field:SerializedName("designStyleId")
	val designStyleId: Int ,

	@field:SerializedName("imageUrl")
	val imageUrl: String ,

	@field:SerializedName("designTypeId")
	val designTypeId: Int ,

	@field:SerializedName("id")
	val id: Int ,

	@field:SerializedName("updatedAt")
	val updatedAt: String 
)

data class DataPost(

	@field:SerializedName("posts")
	val posts: List<PostsItem>
)
