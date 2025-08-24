package com.thamaneya.androidchallenge.core.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Room entity for storing remote keys for pagination
 */
@Entity(tableName = "remote_keys")
data class RemoteKeysEntity(
    @PrimaryKey
    val sectionId: String,
    val prevKey: Int?,
    val nextKey: Int?,
    val lastUpdated: Long = System.currentTimeMillis()
)


