package com.dicoding.illustrateme.view.user

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.illustrateme.api.ApiConfig
import com.dicoding.illustrateme.model.GetUserInfoResponse
import com.dicoding.illustrateme.model.DataUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserViewModel : ViewModel() {
    val detailUser = MutableLiveData<DataUser>()

    fun setUser(token: String, username: String) {
        val client = ApiConfig.getApiService().getUserInfo("Bearer $token",username)
        client.enqueue(object : Callback<GetUserInfoResponse> {
            override fun onResponse(
                call: Call<GetUserInfoResponse>,
                response: Response<GetUserInfoResponse>
            ) {
                if (response.isSuccessful) {
                    detailUser.postValue(response.body()?.data)
                }
            }

            override fun onFailure(call: Call<GetUserInfoResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })
    }

    fun getUser(): LiveData<DataUser> {
        return detailUser
    }

    companion object {
        private const val TAG = "UserViewModel"
    }
}