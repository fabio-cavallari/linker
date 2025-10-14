package com.fabiocavallari.linker.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fabiocavallari.linker.data.model.DataError
import com.fabiocavallari.linker.domain.model.Alias
import com.fabiocavallari.linker.domain.model.Resource
import com.fabiocavallari.linker.domain.usecase.CreateAliasUseCase
import com.fabiocavallari.linker.presentation.intent.HomeIntent
import com.fabiocavallari.linker.presentation.intent.HomeUiEffect
import com.fabiocavallari.linker.presentation.state.HomeScreenUiState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class HomeScreenViewModel(
    val createAliasUseCase: CreateAliasUseCase
): ViewModel() {
    private val _state: MutableStateFlow<HomeScreenUiState> =
        MutableStateFlow(HomeScreenUiState())
    val state: StateFlow<HomeScreenUiState> = _state

    private val _uiEvent = MutableSharedFlow<HomeUiEffect>()
    val uiEvent = _uiEvent.asSharedFlow()

    fun onIntent(intent: HomeIntent) {
        when (intent) {
            is HomeIntent.OnSubmitLink -> submitLink(intent.link)
            is HomeIntent.OnTextChanged -> {
                _state.value = state.value.copy(link = intent.link)
            }
        }
    }

    fun submitLink(text: String) {
        viewModelScope.launch {
            _state.value = state.value.copy(isTextFieldLoading = true)
            val resource = createAliasUseCase.createAlias(text)
            when (resource) {
                is Resource.Success -> {
                    addLinkToHistory(resource.data)
                    _uiEvent.emit(HomeUiEffect.OnSubmitLinkError(DataError.Network.UNKNOWN))
                }
                is Resource.Error -> {
                    _state.value = state.value.copy(isTextFieldLoading = false)
                    _uiEvent.emit(HomeUiEffect.OnSubmitLinkError(resource.error))
                }
            }
        }
    }

    fun addLinkToHistory(link: Alias) {
        val historyList = state.value.historyList
        historyList.add(link)
        _state.value = state.value.copy(isTextFieldLoading = false, historyList = historyList)
    }
}