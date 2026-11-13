package com.example.unscramble

import com.example.unscramble.data.MAX_NO_OF_WORDS
import com.example.unscramble.data.SCORE_INCREASE
import com.example.unscramble.data.getUnscrambledWord
import com.example.unscramble.ui.GameViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class GameViewModelTest {

    private val viewModel = GameViewModel()

    @Test
    fun gameViewModel_CorrectWordGuessed_ScoreUpdatedAndErrorFlagUnset() {
        var currentGameUiState = viewModel.uiState.value
        val correctPlayerWord = getUnscrambledWord(currentGameUiState.currentScrambledWord)

        viewModel.updateUserGuess(correctPlayerWord)
        viewModel.checkUserGuess()

        currentGameUiState = viewModel.uiState.value
        assertFalse(currentGameUiState.isGuessedWordWrong)
        assertEquals(SCORE_AFTER_FIRST_CORRECT_ANSWER, currentGameUiState.score)
    }

    @Test
    fun gameViewModel_IncorrectGuess_ErrorFlagSet() {
        val incorrectPlayerWord = "qwerty"

        viewModel.updateUserGuess(incorrectPlayerWord)
        viewModel.checkUserGuess()

        val currentGameUiState = viewModel.uiState.value
        assertTrue(currentGameUiState.isGuessedWordWrong)
        assertEquals(0, currentGameUiState.score)
        assertEquals(1, currentGameUiState.currentWordCount)
    }

    @Test
    fun gameViewModel_Initialization_FirstWordLoaded() {
        val currentGameUiState = viewModel.uiState.value

        assertTrue(currentGameUiState.currentScrambledWord.isNotEmpty())
        assertEquals(0, currentGameUiState.score)
        assertEquals(1, currentGameUiState.currentWordCount)
        assertFalse(currentGameUiState.isGuessedWordWrong)
        assertFalse(currentGameUiState.isGameOver)
    }

    @Test
    fun gameViewModel_AllWordsGuessed_UiStateUpdatedCorrectly() {
        var expectedScore = 0
        var currentGameUiState = viewModel.uiState.value
        var iterations = 0

        while (currentGameUiState.currentWordCount <= MAX_NO_OF_WORDS && iterations < 100) {
            val correctPlayerWord = getUnscrambledWord(currentGameUiState.currentScrambledWord)
            viewModel.updateUserGuess(correctPlayerWord)
            viewModel.checkUserGuess()

            expectedScore += SCORE_INCREASE
            currentGameUiState = viewModel.uiState.value
            iterations++
        }

        assertTrue(currentGameUiState.isGameOver)
        assertEquals(expectedScore, currentGameUiState.score)
    }

    @Test
    fun gameViewModel_WordSkipped_ScoreUnchangedAndWordCountIncreased() {
        val initialGameUiState = viewModel.uiState.value

        viewModel.skipWord()

        val currentGameUiState = viewModel.uiState.value
        assertEquals(initialGameUiState.score, currentGameUiState.score)
        assertEquals(
            initialGameUiState.currentWordCount + 1,
            currentGameUiState.currentWordCount
        )
        assertTrue(currentGameUiState.currentScrambledWord.isNotEmpty())
    }

    companion object {
        private const val SCORE_AFTER_FIRST_CORRECT_ANSWER = SCORE_INCREASE
    }
}
