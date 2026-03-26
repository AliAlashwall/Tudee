package com.example.tudee.data.local.dataStore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OnboardingPreferences @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private val ONBOARDING_KEY =
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
