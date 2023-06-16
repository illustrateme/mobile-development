package com.dicoding.illustrateme.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.illustrateme.model.Data
import com.dicoding.illustrateme.preference.AuthPreference
import kotlinx.coroutines.launch

class AuthViewModel(private val pref: AuthPreference): ViewModel() {
    fun getUser(): LiveData<Data> {
        return pref.getUser().asLiveData()
    }

    fun signIn(token: String, name : String, email : String, username : String) {
        viewModelScope.launch {
            pref.signIn(token, name, email, username)
        }
    }

    fun signOut() {
        viewModelScope.launch {
            pref.signOut()
        }
    }
}