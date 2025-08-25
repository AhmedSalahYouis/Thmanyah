package com.thamaneya.androidchallenge.core.data.local

import androidx.room.TypeConverter
import com.thamaneya.androidchallenge.core.model.ContentType
import com.thamaneya.androidchallenge.core.model.SectionLayout

/**
 * Room type converters for enums
 */
class Converters {
    
    @TypeConverter
    fun fromSectionLayout(layout: SectionLayout): String {
        return layout.name
    }
    
    @TypeConverter
    fun toSectionLayout(layout: String): SectionLayout {
        return try {
            SectionLayout.valueOf(layout)
        } catch (e: IllegalArgumentException) {
            SectionLayout.UNKNOWN
        }
    }
    
    @TypeConverter
    fun fromContentType(contentType: ContentType): String {
        return contentType.name
    }
    
    @TypeConverter
    fun toContentType(contentType: String): ContentType {
        return try {
            ContentType.valueOf(contentType)
        } catch (e: IllegalArgumentException) {
            ContentType.UNKNOWN
        }
    }
}



