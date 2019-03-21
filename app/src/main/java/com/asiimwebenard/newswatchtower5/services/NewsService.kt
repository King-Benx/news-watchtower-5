package com.asiimwebenard.newswatchtower5.services

import com.asiimwebenard.newswatchtower5.models.NewsReport
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface NewsService {
    @GET("everything")
    fun getNewsUpdates(@QueryMap filter: HashMap<String, String>): Call<NewsReport>

    @GET("top-headlines")
    fun getTopHeadlines(@QueryMap filter: HashMap<String, String>): Call<NewsReport>
}
