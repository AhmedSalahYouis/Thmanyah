package com.thamaneya.androidchallenge.core.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

/**
 * DAO for HomeItem database operations
 */
@Dao
interface HomeItemDao {

    @Query("SELECT * FROM home_items WHERE sectionId = :sectionId ORDER BY `order` ASC")
    fun getItemsBySectionId(sectionId: String): List<HomeItemEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItems(items: List<HomeItemEntity>)

    @Query("DELETE FROM home_items")
    suspend fun deleteAllItems()
    
    @Query("SELECT COUNT(*) FROM home_items WHERE sectionId = :sectionId")
    suspend fun getItemCountBySection(sectionId: String): Int
}



