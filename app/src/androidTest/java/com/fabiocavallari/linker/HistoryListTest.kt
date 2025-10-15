package com.fabiocavallari.linker

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import com.fabiocavallari.linker.domain.model.Alias
import com.fabiocavallari.linker.presentation.component.HistoryList
import com.fabiocavallari.linker.presentation.theme.LinkerTheme
import org.junit.Rule
import org.junit.Test

class HistoryListTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun whenListIsEmpty_shouldDisplayEmptyState() {
        // Given
        val emptyList = emptyList<Alias>()

        // When
        composeTestRule.setContent {
            LinkerTheme {
                HistoryList(items = emptyList)
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
    fun whenListHasItems_shouldDisplayAllItems() {
        // Given
        val items = listOf(
            Alias(
                alias = "google",
                short = "https://short.url/google",
                original = "https://www.google.com"
            ),
            Alias(
                alias = "github",
                short = "https://short.url/github",
                original = "https://www.github.com"
            )
        )

        // When
        composeTestRule.setContent {
            LinkerTheme {
                HistoryList(items = items)
            }
        }

        // Then
        composeTestRule
            .onNodeWithText("google")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("github")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("https://short.url/google")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("https://short.url/github")
            .assertIsDisplayed()
    }

    @Test
    fun whenListHasItems_shouldNotDisplayEmptyState() {
        // Given
        val items = listOf(
            Alias(
                alias = "test",
                short = "https://short.url/test",
                original = "https://www.test.com"
            )
        )

        // When
        composeTestRule.setContent {
            LinkerTheme {
                HistoryList(items = items)
            }
        }

        // Then
        composeTestRule
            .onNodeWithText("Your list is empty")
            .assertDoesNotExist()
    }

    @Test
    fun whenListHasItems_shouldDisplayShareAndOpenButtons() {
        // Given
        val items = listOf(
            Alias(
                alias = "test",
                short = "https://short.url/test",
                original = "https://www.test.com"
            )
        )

        // When
        composeTestRule.setContent {
            LinkerTheme {
                HistoryList(items = items)
            }
        }

        // Then
        composeTestRule
            .onNodeWithContentDescription("Share link")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithContentDescription("Open link")
            .assertIsDisplayed()
    }
}