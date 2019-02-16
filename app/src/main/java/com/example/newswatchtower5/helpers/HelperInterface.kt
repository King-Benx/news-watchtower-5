package com.example.newswatchtower5.helpers

import com.example.newswatchtower5.models.Article

interface HelperInterface {
    fun loadDefaultFragment()

    fun loadDetailView(article: Article)
}

