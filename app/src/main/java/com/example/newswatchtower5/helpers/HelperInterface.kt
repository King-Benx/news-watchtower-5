package com.example.newswatchtower5.helpers

import com.example.newswatchtower5.dao.StoredArticle
import com.example.newswatchtower5.models.Article

interface HelperInterface {
    fun loadDefaultFragment()

    fun loadDetailView(article: Article)

    fun storeArticle(storedArticle: StoredArticle)

    fun progressStatus(status: Boolean)

    fun loadSavedArticles()

    fun deleteSavedArticle(storedArticle: StoredArticle)

}

