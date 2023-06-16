package com.dicoding.illustrateme.preference

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.dicoding.illustrateme.model.Data
import com.dicoding.illustrateme.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AuthPreference private constructor(private val dataStore: DataStore<Preferences>) {

    fun getUser(): Flow<Data> {
        return dataStore.data.map { preferences ->
            Data(
                User(
                    preferences[PASSWORD_KEY] ?:"",
                    preferences[NAME_KEY] ?:"",
                    preferences[USERID_KEY] ?:"",
                    preferences[EMAIL_KEY] ?:"",
                    preferences[USERNAME_KEY] ?:"",
                    preferences[REGISTEREDAT_KEY] ?:"",
                    preferences[UPDATEDAT_KEY] ?:"",
                ),
                preferences[TOKEN_KEY] ?:"",
                preferences[STATE_KEY] ?: false
            )
        }
    }

    suspend fun signIn(token : String, name : String, email : String, username : String) {
        dataStore.edit { preferences ->
            preferences[TOKEN_KEY] = token
            preferences[NAME_KEY] = name
            preferences[EMAIL_KEY] = email
            preferences[USERNAME_KEY] = username
            preferences[STATE_KEY] = true
        }
    }

    suspend fun signOut() {
        dataStore.edit { preferences ->
            preferences[TOKEN_KEY] = ""
            preferences[NAME_KEY] = ""
            preferences[EMAIL_KEY] = ""
            preferences[STATE_KEY] = false
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: AuthPreference? = null

        private val PASSWORD_KEY = stringPreferencesKey("password")
        private val NAME_KEY = stringPreferencesKey("name")
        private val USERID_KEY = stringPreferencesKey("userid")
        private val EMAIL_KEY = stringPreferencesKey("email")
        private val USERNAME_KEY = stringPreferencesKey("username")
        private val REGISTEREDAT_KEY = stringPreferencesKey("registeredat")
        private val UPDATEDAT_KEY = stringPreferencesKey("updatedat")
        private val TOKEN_KEY = stringPreferencesKey("token")
        private val STATE_KEY = booleanPreferencesKey("state")

        fun getInstance(dataStore: DataStore<Preferences>): AuthPreference {
            return INSTANCE ?: synchronized(this) {
                val instance = AuthPreference(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}