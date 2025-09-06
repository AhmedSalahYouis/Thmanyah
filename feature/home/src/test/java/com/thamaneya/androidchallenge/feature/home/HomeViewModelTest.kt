package com.thamaneya.androidchallenge.feature.home

import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListUpdateCallback
import com.thamaneya.androidchallenge.core.data.home.HomeRepository
import com.thamaneya.androidchallenge.core.model.ContentType
import com.thamaneya.androidchallenge.core.model.HomeSection
import com.thamaneya.androidchallenge.core.model.PodcastItem
import com.thamaneya.androidchallenge.core.model.SectionLayout
import com.thamaneya.logger.logging.ITimberLogger
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    // ---- Test dispatcher/scope setup
    private val dispatcher = StandardTestDispatcher()
    private val scope = TestScope(dispatcher)

    // ---- Test doubles
    private lateinit var fakeRepo: FakeHomeRepository
    private val logger: ITimberLogger = mock()

    private lateinit var viewModel: HomeViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
        fakeRepo = FakeHomeRepository()
        viewModel = HomeViewModel(fakeRepo, logger)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    // ---- Helpers ----

    private val diffCallback = object : DiffUtil.ItemCallback<HomeSection>() {
        override fun areItemsTheSame(oldItem: HomeSection, newItem: HomeSection): Boolean {
            // If HomeSection has an id, use it; otherwise fallback to equals
            return oldItem == newItem
        }
        override fun areContentsTheSame(oldItem: HomeSection, newItem: HomeSection) = oldItem == newItem
    }

    private suspend fun PagingData<HomeSection>.toList(): List<HomeSection> {
        val differ = AsyncPagingDataDiffer(
            diffCallback = diffCallback,
            updateCallback = NoopListUpdateCallback,
            mainDispatcher = dispatcher,
            workerDispatcher = dispatcher
        )
        differ.submitData(this)
        // Let coroutines progress
        dispatcher.scheduler.advanceUntilIdle()
        return differ.snapshot().items
    }

    private object NoopListUpdateCallback : ListUpdateCallback {
        override fun onInserted(position: Int, count: Int) {}
        override fun onRemoved(position: Int, count: Int) {}
        override fun onMoved(fromPosition: Int, toPosition: Int) {}
        override fun onChanged(position: Int, count: Int, payload: Any?) {}
    }

    private fun sampleSections(): List<HomeSection> {
        // Create minimal HomeSection objects used by the ViewModel (must have contentType).
        // If your real HomeSection has more required params, fill stubs here.
        return listOf(
            section(type = ContentType.PODCAST, name = "Pod A"),
            section(type = ContentType.EPISODE, name = "Track A"),
            section(type = ContentType.AUDIO_BOOK, name = "Pod B"),
            section(type = ContentType.AUDIO_ARTICLE, name = "Book A"),
            section(type = ContentType.UNKNOWN, name = "News A")
        )
    }

    private fun section(type: ContentType, name: String): HomeSection {
        // Replace with your real constructor
        return HomeSection(
            // placeholder fields; adjust names/types to your actual model
            name = name,
            contentType = type,
            order = 0,
            layout = SectionLayout.SQUARE,
            items = listOf(PodcastItem.Default),
        )
    }

    // ---- Tests ----

    @Test
    fun selecting_a_content_type_filters_display_sections() = scope.runTest {
        val items = sampleSections()
        fakeRepo.emit(PagingData.from(items))

        viewModel.selectContentType(ContentType.PODCAST)
        // Advance jobs
        dispatcher.scheduler.advanceUntilIdle()

        val filtered = viewModel.displayHomeSections.first().toList()
        assertTrue(filtered.all { it.contentType == ContentType.PODCAST })
        // Expect exactly the two podcast items from sampleSections()
        assertEquals(  1, filtered.size)
    }

    @Test
    fun clearing_selection_shows_all_sections_again() = scope.runTest {
        val items = sampleSections()
        fakeRepo.emit(PagingData.from(items))

        // Apply filter first
        viewModel.selectContentType(ContentType.EPISODE)
        dispatcher.scheduler.advanceUntilIdle()
        val filtered = viewModel.displayHomeSections.first().toList()
        assertEquals(1, filtered.size)
        assertTrue(filtered.all { it.contentType == ContentType.EPISODE })

        // Clear filter
        viewModel.selectContentType(null)
        dispatcher.scheduler.advanceUntilIdle()
        val allAgain = viewModel.displayHomeSections.first().toList()

        assertEquals(items.size, allAgain.size)
        assertEquals(items, allAgain)
    }

    @Test
    fun getAvailableContentTypes_excludes_UNKNOWN() {
        val list = viewModel.getAvailableContentTypes()
        assertTrue(ContentType.UNKNOWN !in list)
        // sanity: there should still be at least one type
        assertTrue(list.isNotEmpty())
    }
}

/**
 * A simple fake repository that lets tests push PagingData emissions.
 */
private class FakeHomeRepository : HomeRepository {
    private val flow = MutableSharedFlow<PagingData<HomeSection>>(replay = 1)

    override fun pagedSections() = flow

    suspend fun emit(paging: PagingData<HomeSection>) {
        flow.emit(paging)
    }
}
