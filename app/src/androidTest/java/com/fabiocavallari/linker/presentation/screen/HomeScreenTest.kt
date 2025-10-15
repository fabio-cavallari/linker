package com.fabiocavallari.linker.presentation.screen

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.fabiocavallari.linker.domain.model.Alias
import com.fabiocavallari.linker.presentation.state.HomeScreenUiState
import com.fabiocavallari.linker.presentation.theme.LinkerTheme
import org.junit.Rule
import org.junit.Test

class HomeScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun whenHistoryListIsEmpty_shouldDisplayEmptyMessage() {
        // Given
        val emptyState = HomeScreenUiState(
            historyList = linkedSetOf()
        )

        // When
        composeTestRule.setContent {
            LinkerTheme {
                HomeScreen(state = emptyState, onIntent = {})
            }
        }

        // Then
        composeTestRule
            .onNodeWithText("Your list is empty")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Shorten a link to see it here.")
            .assertIsDisplayed()
    }

    @Test
    fun whenHistoryListHasItems_shouldNotDisplayEmptyMessage() {
        // Given
        val stateWithItems = HomeScreenUiState(
            historyList = linkedSetOf(
                Alias(
                    alias = "test-alias",
                    short = "https://short.url/test",
                    original = "https://original-url.com"
                )
            )
        )

        // When
        composeTestRule.setContent {
            LinkerTheme {
                HomeScreen(state = stateWithItems, onIntent = {})
            }
        }

        // Then
        composeTestRule
            .onNodeWithText("Your list is empty")
            .assertDoesNotExist()

        composeTestRule
            .onNodeWithText("test-alias")
            .assertIsDisplayed()
    }

    @Test
    fun shouldDisplayPreviousLinksTitle() {
        // Given
        val state = HomeScreenUiState()

        // When
        composeTestRule.setContent {
            LinkerTheme {
                HomeScreen(state = state, onIntent = {})
            }
        }

        // Then
        composeTestRule
            .onNodeWithText("Previous Links")
            .assertIsDisplayed()
    }

    @Test
    fun shouldDisplayLinkTextField() {
        // Given
        val state = HomeScreenUiState()

        // When
        composeTestRule.setContent {
            LinkerTheme {
                HomeScreen(state = state, onIntent = {})
            }
        }

        // Then
        composeTestRule
            .onNodeWithText("Enter your url")
            .assertIsDisplayed()
    }

    @Test
    fun whenHasMultipleItems_shouldDisplayAllItems() {
        // Given
        val stateWithMultipleItems = HomeScreenUiState(
            historyList = linkedSetOf(
                Alias(
                    alias = "first-alias",
                    short = "https://short.url/first",
                    original = "https://first-url.com"
                ),
                Alias(
                    alias = "second-alias",
                    short = "https://short.url/second",
                    original = "https://second-url.com"
                ),
                Alias(
                    alias = "third-alias",
                    short = "https://short.url/third",
                    original = "https://third-url.com"
                )
            )
        )

        // When
        composeTestRule.setContent {
            LinkerTheme {
                HomeScreen(state = stateWithMultipleItems, onIntent = {})
            }
        }

        // Then
        composeTestRule
            .onNodeWithText("first-alias")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("second-alias")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("third-alias")
            .assertIsDisplayed()
    }
}