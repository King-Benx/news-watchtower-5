package com.example.newswatchtower5.models

import androidx.fragment.app.Fragment

/**
 * 3 classes responsible for how our requests are represented.
 */
data class NewsReport(
    val status: String,
    val totalResults: Long,
    val articles: List<Article>
)

data class Article(
    val source: Source,
    val author: String? = null,
    val title: String,
    val description: String,
    val url: String,
    val urlToImage: String,
    val publishedAt: String,
    val content: String? = null
)

data class Source(
    val id: String? = null,
    val name: String
)

/**
 * Responsible for keep track of our fragments
 */
data class FragmentTag(
    val fragment: Fragment,
    val tag: String
)

