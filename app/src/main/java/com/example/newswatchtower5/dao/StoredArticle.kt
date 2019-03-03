package com.example.newswatchtower5.dao

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "stored_articles")
@Parcelize
class StoredArticle(
    @PrimaryKey
    val id: String,
    val source: String,
    val author: String? = null,
    val title: String,
    val description: String,
    val url: String,
    val urlToImage: String,
    val publishedAt: String
) : Parcelable
