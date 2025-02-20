package com.michalm.movies.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.michalm.movies.data.SharedPreferencesHelper
import com.michalm.movies.network.ApiService
import com.michalm.movies.ui.MovieViewModel
import com.michalm.movies.utils.MockData
import com.michalm.movies.utils.ResponseState
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkAll
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.IOException

@ExperimentalCoroutinesApi
class MovieViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val apiService: ApiService = mockk()
    private val sharedPreferencesHelper: SharedPreferencesHelper = mockk(relaxed = true)
    private lateinit var viewModel: MovieViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
        viewModel = MovieViewModel(apiService, sharedPreferencesHelper)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        unmockkAll()
    }

    @Test
    fun `loadMovies should fetch movies when conditions are met`() = runTest {

        val mockResponse = MockData.mockNowPlayingResponse
        coEvery { apiService.fetchNowPlayingMovies(any(), any()) } returns mockResponse
        every { sharedPreferencesHelper.getFavoriteMovies() } returns setOf()

        viewModel.loadMovies()
        advanceUntilIdle()

        coVerify { apiService.fetchNowPlayingMovies(any(), any()) }
        assertEquals(1, viewModel.movieListResult.value.size)
        assertEquals("Test Movie Title", viewModel.movieListResult.value[0].title)
        assert(viewModel.responseState.value is ResponseState.Success)
    }

    @Test
    fun `fetchMovies should handle API error`() = runTest {

        val expectedErrorMessage = "Network Error"
        coEvery {
            apiService.fetchNowPlayingMovies(
                any(),
                any()
            )
        } throws IOException(expectedErrorMessage)

        viewModel.loadMovies()
        advanceUntilIdle()

        assert(viewModel.responseState.value is ResponseState.Error)
        assertEquals(
            expectedErrorMessage,
            (viewModel.responseState.value as ResponseState.Error).message
        )
    }

    @Test
    fun `selectMovie should update selected movie`() {

        val movie = MockData.mockMovie

        viewModel.selectMovie(movie)

        assertEquals(movie, viewModel.movie.value)
    }

    @Test
    fun `searchMovies should update search results`() = runTest {

        val mockResponse = MockData.mockMovieSearchResponse
        val searchQuery = "Search Query"
        coEvery { apiService.searchMovies(any(), any()) } returns mockResponse
        every { sharedPreferencesHelper.getFavoriteMovies() } returns setOf()

        viewModel.searchMovies(searchQuery)
        advanceUntilIdle()

        coVerify { apiService.searchMovies(any(), any()) }
        assertEquals(1, viewModel.searchResults.value.size)
    }

    @Test
    fun `searchMovies should clear results for blank query`() {

        viewModel.searchMovies("")

        assert(viewModel.searchResults.value.isEmpty())
    }
}