package com.fabiocavallari.linker.presentation.component

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.fabiocavallari.linker.presentation.theme.LinkerTheme
import org.junit.Rule
import org.junit.Test

class LinkTextFieldTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun shouldDisplayPlaceholder() {
        // When
        composeTestRule.setContent {
            LinkerTheme {
                LinkTextField()
            }
        }

        // Then
        composeTestRule
            .onNodeWithText("Enter your url")
            .assertIsDisplayed()
    }

    @Test
    fun whenTextIsEmpty_sendButtonShouldBeDisabled() {
        // When
        composeTestRule.setContent {
            LinkerTheme {
                LinkTextField(link = "")
            }
        }

        // Then
        composeTestRule
            .onNodeWithContentDescription("Send")
            .assertIsNotEnabled()
    }

    @Test
    fun whenTextIsNotEmpty_sendButtonShouldBeEnabled() {
        // When
        composeTestRule.setContent {
            LinkerTheme {
                LinkTextField(link = "https://sample-url.com")
            }
        }

        // Then
        composeTestRule
            .onNodeWithContentDescription("Send")
            .assertIsEnabled()
    }

    @Test
    fun whenUrlIsInvalid_shouldDisplayErrorMessageAndSendButtonIsDisabled() {
        // When
        composeTestRule.setContent {
            LinkerTheme {
                LinkTextField(
                    link = "invalid-url",
                    isInvalidUrl = true
                )
            }
        }

        // Then
        composeTestRule
            .onNodeWithText("your url is invalid")
            .assertIsDisplayed()
        composeTestRule
            .onNodeWithContentDescription("Send")
            .assertIsNotEnabled()
    }

    @Test
    fun whenUrlIsValid_shouldNotDisplayErrorMessage() {
        // When
        composeTestRule.setContent {
            LinkerTheme {
                LinkTextField(
                    link = "https://sample-url.com",
                    isInvalidUrl = false
                )
            }
        }

        // Then
        composeTestRule
            .onNodeWithText("your url is invalid")
            .assertDoesNotExist()
    }

    @Test
    fun whenIsLoading_shouldDisplayProgressIndicator() {
        // When
        composeTestRule.setContent {
            LinkerTheme {
                LinkTextField(
                    link = "https://sample-url.com",
                    isLoading = true
                )
            }
        }

        // Then
        composeTestRule
            .onNodeWithContentDescription("Send")
            .assertDoesNotExist()
    }

    @Test
    fun whenIsNotLoading_shouldDisplaySendButton() {
        // When
        composeTestRule.setContent {
            LinkerTheme {
                LinkTextField(
                    link = "https://sample-url.com",
                    isLoading = false
                )
            }
        }

        // Then
        composeTestRule
            .onNodeWithContentDescription("Send")
            .assertIsDisplayed()
    }

    @Test
    fun shouldCallOnTextChangedWhenTyping() {
        // Given
        var textChanged = false
        var capturedText = ""

        composeTestRule.setContent {
            LinkerTheme {
                LinkTextField(
                    link = "",
                    onTextChanged = { text ->
                        textChanged = true
                        capturedText = text
                    }
                )
            }
        }

        // When
        composeTestRule
            .onNodeWithText("Enter your url")
            .performTextInput("https://sample-url.com")

        // Then
        assert(textChanged)
        assert(capturedText == "https://sample-url.com")
    }

    @Test
    fun shouldCallOnSubmitLinkWhenClickingSendButton() {
        // Given
        var submitCalled = false
        var submittedLink = ""

        composeTestRule.setContent {
            LinkerTheme {
                LinkTextField(
                    link = "https://sample-url.com",
                    onSubmitLink = { link ->
                        submitCalled = true
                        submittedLink = link
                    }
                )
            }
        }

        // When
        composeTestRule
            .onNodeWithContentDescription("Send")
            .performClick()

        // Then
        assert(submitCalled)
        assert(submittedLink == "https://sample-url.com")
    }
}