package com.fabiocavallari.linker.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fabiocavallari.linker.domain.model.Alias
import com.fabiocavallari.linker.domain.model.Resource
import com.fabiocavallari.linker.domain.usecase.CreateAliasUseCase
import com.fabiocavallari.linker.presentation.intent.HomeIntent
import com.fabiocavallari.linker.presentation.state.HomeScreenUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeScreenViewModel(
    val createAliasUseCase: CreateAliasUseCase
): ViewModel() {
    private val _state: MutableStateFlow<HomeScreenUiState> =
        MutableStateFlow(HomeScreenUiState())
    val state: StateFlow<HomeScreenUiState> = _state

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
                }
                is Resource.Error -> {
                    _state.value = state.value.copy(isTextFieldLoading = false)
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