package com.example.tudee.data.local

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class OnboardingPreferences(
    private val context: Context
) {

    val ONBOARDING_KEY =
        booleanPreferencesKey("onboarding_completed")

    fun isOnboardingCompleted(): Flow<Boolean> =
        context.dataStore.data.map { pref ->
            pref[ONBOARDING_KEY] ?: false
        }

    suspend fun saveOnboardingCompleted() {
        context.dataStore.edit { pref ->
            pref[ONBOARDING_KEY] = true
        }
    }
}
