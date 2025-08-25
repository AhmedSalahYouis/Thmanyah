package com.thamaneya.androidchallenge.core.data

import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.thamaneya.androidchallenge.core.data.home.HomeRemoteMediator
import com.thamaneya.androidchallenge.core.data.home.local.AppDatabase
import com.thamaneya.androidchallenge.core.data.mapper.HomeSectionMapper
import com.thamaneya.androidchallenge.core.network.HomeApi
import com.thamaneya.androidchallenge.core.network.HomeResponseDto
import com.thamaneya.androidchallenge.core.network.PaginationDto
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class HomeRemoteMediatorTest {
    
    private lateinit var database: AppDatabase
    private lateinit var api: HomeApi
    private lateinit var mapper: HomeSectionMapper
    private lateinit var remoteMediator: HomeRemoteMediator
    
    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).build()
        
        api = mockk()
        mapper = HomeSectionMapper()
        remoteMediator = HomeRemoteMediator(api, database, mapper)
    }
    
    @After
    fun tearDown() {
        database.close()
    }
    
    @Test
    fun `load returns success for valid data`() = runTest {
        // Given
        val mockResponse = HomeResponseDto(
            sections = emptyList(),
            pagination = PaginationDto(nextPage = null, totalPages = 1)
        )
        coEvery { api.getHomeSections(any()) } returns mockResponse
        
        val state = PagingState<Int, Any>(
            pages = emptyList(),
            anchorPosition = null,
            config = androidx.paging.PagingConfig(10),
            initialKey = null
        )
        
        // When
        val result = remoteMediator.load(LoadType.REFRESH, state)
        
        // Then
        assertTrue(result is RemoteMediator.MediatorResult.Success)
        val successResult = result as RemoteMediator.MediatorResult.Success
        assertTrue(successResult.endOfPaginationReached)
    }
    
    @Test
    fun `load returns error for network failure`() = runTest {
        // Given
        coEvery { api.getHomeSections(any()) } throws IOException("Network error")
        
        val state = PagingState<Int, Any>(
            pages = emptyList(),
            anchorPosition = null,
            config = androidx.paging.PagingConfig(10),
            initialKey = null
        )
        
        // When
        val result = remoteMediator.load(LoadType.REFRESH, state)
        
        // Then
        assertTrue(result is RemoteMediator.MediatorResult.Error)
        val errorResult = result as RemoteMediator.MediatorResult.Error
        assertTrue(errorResult.throwable is IOException)
    }
}




