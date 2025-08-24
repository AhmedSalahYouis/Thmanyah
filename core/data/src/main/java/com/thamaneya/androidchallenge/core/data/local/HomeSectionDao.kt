package com.thamaneya.androidchallenge.core.data.local

import androidx.paging.PagingSource
import androidx.room.*
import kotlinx.coroutines.flow.Flow

/**
 * DAO for HomeSection database operations
 */
@Dao
interface HomeSectionDao {
    
    @Query("SELECT * FROM home_sections ORDER BY `order` ASC")
    fun getAllSections(): Flow<List<HomeSectionEntity>>
    
    @Query("SELECT * FROM home_sections ORDER BY `order` ASC")
    fun pagingSource(): PagingSource<Int, HomeSectionEntity>
    
    @Query("SELECT * FROM home_sections WHERE page = :page ORDER BY `order` ASC")
    fun getSectionsByPage(page: Int): List<HomeSectionEntity>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSections(sections: List<HomeSectionEntity>)
    
    @Query("DELETE FROM home_sections WHERE page = :page")
    suspend fun deleteSectionsByPage(page: Int)
    
    @Query("DELETE FROM home_sections")
    suspend fun deleteAllSections()
    
    @Query("SELECT COUNT(*) FROM home_sections")
    suspend fun getSectionCount(): Int
    
    @Query("SELECT * FROM home_sections WHERE id = :sectionId")
    suspend fun getSectionById(sectionId: String): HomeSectionEntity?
}
