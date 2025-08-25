package com.thamaneya.androidchallenge.core.data.local

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.thamaneya.androidchallenge.core.model.ContentType

/**
 * Room entity for HomeItem
 */
@Entity(
    tableName = "home_items",
    foreignKeys = [
        ForeignKey(
            entity = HomeSectionEntity::class,
            parentColumns = ["id"],
            childColumns = ["sectionId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class HomeItemEntity(
    @PrimaryKey
    val id: String,
    val sectionId: String,
    val name: String,
    val description: String,
    val avatarUrl: String,
    val duration: Int?,
    val contentType: ContentType,
    val order: Int,
    val lastUpdated: Long = System.currentTimeMillis()
)


