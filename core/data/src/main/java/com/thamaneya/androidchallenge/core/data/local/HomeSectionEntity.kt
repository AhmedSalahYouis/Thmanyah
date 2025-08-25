package com.thamaneya.androidchallenge.core.data.local

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.thamaneya.androidchallenge.core.model.ContentType
import com.thamaneya.androidchallenge.core.model.SectionLayout

/**
 * Room entity for HomeSection
 */
@Entity(tableName = "home_sections")
data class HomeSectionEntity(
    @PrimaryKey
    val id: String, // Composite key: section name + order
    val name: String,
    val order: Int,
    val layout: SectionLayout,
    val contentType: ContentType,
    val page: Int,
    val lastUpdated: Long = System.currentTimeMillis()
)



