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
) : ViewModel() {
    private val _state: MutableStateFlow<HomeScreenUiState> =
        MutableStateFlow(HomeScreenUiState())
    val state: StateFlow<HomeScreenUiState> = _state

    fun onIntent(intent: HomeIntent) {
        when (intent) {
            is HomeIntent.OnSubmitLink -> submitLink(text = intent.link)
            is HomeIntent.OnTextChanged -> {
                _state.value = state.value.copy(link = intent.link)
            }
            HomeIntent.OnDismissDialog -> dismissErrorDialog()
            HomeIntent.OnDismissAliasDialog -> dismissAliasDialog()
        }
    }

    fun submitLink(text: String) {
        viewModelScope.launch {
            _state.value = state.value.copy(isTextFieldLoading = true)
            val resource = createAliasUseCase.createAlias(text)
            when (resource) {
                is Resource.Success -> {
                    addLinkToHistory(link = resource.data)
                }

                is Resource.Error -> {
                    _state.value =
                        state.value.copy(isTextFieldLoading = false, dialogError = resource.error)
                }
            }
        }
    }

    private fun addLinkToHistory(link: Alias) {
        val historyList = state.value.historyList
        historyList.add(link)
        _state.value = state.value.copy(
            link = "",
            isTextFieldLoading = false,
            historyList = historyList,
            selectedAlias = link,
        )
    }

    private fun dismissErrorDialog() {
        _state.value = state.value.copy(dialogError = null)
    }

    private fun dismissAliasDialog() {
        _state.value = state.value.copy(selectedAlias = null)
    }
}