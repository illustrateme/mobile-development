package com.dicoding.illustrateme.view.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.illustrateme.api.ApiConfig
import com.dicoding.illustrateme.model.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    val listPost = MutableLiveData<List<PostsItem>>()

    fun setPost(token: String) {
        val client = ApiConfig.getApiService().getAllPosts("Bearer $token")
        client.enqueue(object : Callback<PostResponse> {
            override fun onResponse(call: Call<PostResponse>, response: Response<PostResponse>) {
                if (response.isSuccessful) {
                    listPost.postValue(response.body()?.data?.posts)
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<PostResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })
    }
    
    fun getPosts(): LiveData<List<PostsItem>> {
        return listPost
    }

    companion object {
        private const val TAG = "MainViewModel"
    }


}