package com.fabiocavallari.linker.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.fabiocavallari.linker.presentation.intent.HomeIntent
import com.fabiocavallari.linker.presentation.state.HomeScreenUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HomeScreenViewModel: ViewModel() {
    private val _state: MutableStateFlow<HomeScreenUiState> =
        MutableStateFlow(HomeScreenUiState())
    val state: StateFlow<HomeScreenUiState> = _state

    fun onIntent(intent: HomeIntent) {
        when (intent) {
            is HomeIntent.PostLink -> Log.d(">>>", "post link")
            is HomeIntent.OnTextChanged -> _state.value = _state.value.copy(link = intent.text)
        }
    }

}