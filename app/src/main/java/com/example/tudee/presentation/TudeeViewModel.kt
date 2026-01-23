package com.example.tudee.presentation

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tudee.data.local.OnboardingPreferences
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TudeeViewModel(context: Context) : ViewModel() {

    val onboardingPreferences = OnboardingPreferences(context)


    val isOnboardingCompleted =
        onboardingPreferences.isOnboardingCompleted().stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    fun completeOnboarding() {
        viewModelScope.launch {
            onboardingPreferences.saveOnboardingCompleted()
        }
    }
}