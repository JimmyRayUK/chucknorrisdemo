package uk.co.jimmyraystudios.chucknorrisjokeschallenge

import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.*
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import uk.co.jimmyraystudios.chucknorrisjokeschallenge.data.NorrisDataSource
import uk.co.jimmyraystudios.chucknorrisjokeschallenge.search.SearchFragmentContract
import uk.co.jimmyraystudios.chucknorrisjokeschallenge.search.SearchFragmentPresenter


class SearchFragmentPresenterTest {

    @Mock
    lateinit var view : SearchFragmentContract.View

    @Mock
    lateinit var dataSource : NorrisDataSource

    lateinit var presenter : SearchFragmentPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = SearchFragmentPresenter(view, dataSource)
    }

    @Test
    fun `when search clicked, if no search terms, error should be shown`() {
        presenter.onSearchClicked("")
        verify(view).showError("Oops", "Please enter a name")
    }

    @Test
    fun `when search clicked, if more than two words, error should be shown`() {
        presenter.onSearchClicked("testing two many words")
        verify(view).showError("Oops", "Please only two names max")
    }

    @Test
    fun `when search clicked, if only one word, text should be sent as first name`() {
        presenter.onSearchClicked("testing")
        verify(dataSource).getCustomNameJoke(any(), matches("testing"), matches(""))
        //verify(view).showJoke("testing")
    }

    @Test
    fun `when search clicked, if two words, text should be sent as first and last name`() {
        presenter.onSearchClicked("testing two")
        verify(dataSource).getCustomNameJoke(any(), matches("testing"), matches("two"))
        //verify(view).showJoke("testing two")
    }

    fun <T> any(): T = Mockito.any<T>()
}