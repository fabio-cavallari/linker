package com.fabiocavallari.linker.presentation.component

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import com.fabiocavallari.linker.domain.model.Alias
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
                alias = "aliasA",
                short = "https://base-url/aliasA",
                original = "https://www.aliasA.com"
            ),
            Alias(
                alias = "aliasB",
                short = "https://base-url/aliasB",
                original = "https://www.aliasB.com"
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
            .onNodeWithText("aliasA")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("aliasB")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("https://base-url/aliasA")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("https://base-url/aliasB")
            .assertIsDisplayed()
    }

    @Test
    fun whenListHasItems_shouldNotDisplayEmptyState() {
        // Given
        val items = listOf(
            Alias(
                alias = "test",
                short = "https://base-url/test",
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
                short = "https://base-url/test",
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