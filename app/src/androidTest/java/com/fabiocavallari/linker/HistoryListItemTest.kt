package com.fabiocavallari.linker

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.fabiocavallari.linker.presentation.component.LinkTextField
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
                LinkTextField(link = "https://example.com")
            }
        }

        // Then
        composeTestRule
            .onNodeWithContentDescription("Send")
            .assertIsEnabled()
    }

    @Test
    fun whenUrlIsInvalid_shouldDisplayErrorMessage() {
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
    }

    @Test
    fun whenUrlIsValid_shouldNotDisplayErrorMessage() {
        // When
        composeTestRule.setContent {
            LinkerTheme {
                LinkTextField(
                    link = "https://valid-url.com",
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
                    link = "https://example.com",
                    isLoading = true
                )
            }
        }

        // Then - O CircularProgressIndicator não tem content description específico,
        // mas podemos verificar que o botão Send não está visível
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
                    link = "https://example.com",
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
    fun whenUrlIsInvalid_sendButtonShouldBeDisabled() {
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
            .onNodeWithContentDescription("Send")
            .assertIsNotEnabled()
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
            .performTextInput("https://example.com")

        // Then
        assert(textChanged)
        assert(capturedText == "https://example.com")
    }

    @Test
    fun shouldCallOnSubmitLinkWhenClickingSendButton() {
        // Given
        var submitCalled = false
        var submittedLink = ""

        composeTestRule.setContent {
            LinkerTheme {
                LinkTextField(
                    link = "https://example.com",
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
        assert(submittedLink == "https://example.com")
    }
}