package com.thamaneya.androidchallenge.core.data.local

import androidx.room.*

/**
 * DAO for RemoteKeys database operations
 */
@Dao
interface RemoteKeysDao {
    
    @Query("SELECT * FROM remote_keys WHERE sectionId = :sectionId")
    suspend fun getRemoteKeyBySectionId(sectionId: String): RemoteKeysEntity?

    @Query("SELECT * FROM remote_keys WHERE sectionId = (SELECT MAX(sectionId) FROM remote_keys)")
    suspend fun getLastRemoteKey(): RemoteKeysEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRemoteKeys(remoteKeys: List<RemoteKeysEntity>)
    
    @Query("DELETE FROM remote_keys WHERE sectionId = :sectionId")
    suspend fun deleteRemoteKeyBySectionId(sectionId: String)
    
    @Query("DELETE FROM remote_keys")
    suspend fun deleteAllRemoteKeys()
}



