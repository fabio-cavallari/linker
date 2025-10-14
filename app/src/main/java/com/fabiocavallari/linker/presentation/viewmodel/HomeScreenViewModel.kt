package com.fabiocavallari.linker.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fabiocavallari.linker.domain.model.Alias
import com.fabiocavallari.linker.domain.model.Resource
import com.fabiocavallari.linker.domain.usecase.CreateAliasUseCase
import com.fabiocavallari.linker.presentation.intent.HomeAction
import com.fabiocavallari.linker.presentation.intent.HomeEvent
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

    private val _uiEvent = MutableSharedFlow<HomeEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    fun onIntent(intent: HomeAction) {
        when (intent) {
            is HomeAction.OnSubmitLink -> submitLink(intent.link)
            is HomeAction.OnTextChanged -> {
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
                }
                is Resource.Error -> {
                    _state.value = state.value.copy(isTextFieldLoading = false)
                    _uiEvent.emit(HomeEvent.OpenErrorDialog(resource.error))
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