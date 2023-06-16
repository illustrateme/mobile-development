package com.dicoding.illustrateme.model

data class SearchResponse(
	val searchResponse: List<SearchResponseItem>
)

data class SearchResponseItem(
	val createdAt: String,
	val designStyleId: Int,
	val imageUrl: String,
	val description: Any,
	val priceStartFrom: Int,
	val designTypeId: Int,
	val id: Int,
	val title: String,
	val authorId: String,
	val updatedAt: String
)

