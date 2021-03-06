package com.asiimwebenard.newswatchtower5

import androidx.fragment.app.Fragment
import com.asiimwebenard.newswatchtower5.dao.StoredArticle
import com.asiimwebenard.newswatchtower5.models.Article
import com.asiimwebenard.newswatchtower5.models.FragmentTag
import com.asiimwebenard.newswatchtower5.models.NewsReport
import com.asiimwebenard.newswatchtower5.models.Source
import com.asiimwebenard.newswatchtower5.newsviews.*
import com.asiimwebenard.newswatchtower5.shared.DetailFragment
import com.asiimwebenard.newswatchtower5.shared.GeneralFragment
import org.hamcrest.CoreMatchers.instanceOf
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThat
import org.junit.Test


class GeneralNewsActivityTest {
    @Test
    fun test_generalFragment_isInstance_of_fragment() {
        val generalFragment = GeneralFragment()
        assertThat(generalFragment, instanceOf(Fragment::class.java))
    }

    @Test
    fun test_internationalFragment_is_instance_of_fragment() {
        val internationalNewsFragment = InternationalNewsFragment()
        assertThat(internationalNewsFragment, instanceOf(Fragment::class.java))
    }

    @Test
    fun test_saved_articleFragment_is_instance_of_fragment() {
        val savedArticleFragment = SavedArticleFragment()
        assertThat(savedArticleFragment, instanceOf(Fragment::class.java))
    }

    @Test
    fun test_detailsFragment_is_instance_of_fragment() {
        val detailFragment = DetailFragment()
        assertThat(detailFragment, instanceOf(Fragment::class.java))
    }

    @Test
    fun test_kampalaFragment_is_instance_of_fragment() {
        val kampalaNewsFragment = KampalaNewsFragment()
        assertThat(kampalaNewsFragment, instanceOf(Fragment::class.java))
    }

    @Test
    fun test_kigaliFragment_is_instance_of_fragment() {
        val kigaliNewsFragment = KigaliNewsFragment()
        assertThat(kigaliNewsFragment, instanceOf(Fragment::class.java))
    }

    @Test
    fun test_lagosFragment_is_instance_of_fragment() {
        val lagosNewsFragment = LagosNewsFragment()
        assertThat(lagosNewsFragment, instanceOf(Fragment::class.java))
    }

    @Test
    fun test_localeNewsFragment_is_instance_of_fragment() {
        val localeNewsFragment = LocaleNewsFragment()
        assertThat(localeNewsFragment, instanceOf(Fragment::class.java))
    }

    @Test
    fun test_nairobiFragment_is_instance_of_fragment() {
        val nairobiNewsFragment = NairobiNewsFragment()
        assertThat(nairobiNewsFragment, instanceOf(Fragment::class.java))
    }

    @Test
    fun test_newYorkFragment_is_instance_of_fragment() {
        val newYorkNewsFragment = NewYorkNewsFragment()
        assertThat(newYorkNewsFragment, instanceOf(Fragment::class.java))
    }

    @Test
    fun test_storedArticles() {
        val storedArticle = StoredArticle(
            "1",
            "source",
            "author",
            "title",
            "description",
            "https://abc.com",
            "https://abcimage.com",
            "2019-01-01"
        )
        assertEquals(storedArticle.title, "title")
        assertEquals(storedArticle.source, "source")
        assertEquals(storedArticle.author, "author")
        assertEquals(storedArticle.description, "description")
        assertEquals(storedArticle.id, "1")
        assertEquals(storedArticle.publishedAt, "2019-01-01")
        assertEquals(storedArticle.url, "https://abc.com")
        assertEquals(storedArticle.describeContents(), 0)
        assertEquals(storedArticle.urlToImage, "https://abcimage.com")
    }

    @Test
    fun test_newsReports() {
        val source = Source("1", "bbc")
        val source2 = Source("1", "bbc")
        assertEquals(source == source2, true)
        assertEquals(source.copy("2", "cnn").name, "cnn")
        assertEquals(source.id, "1")
        assertEquals(source.describeContents(), 0)
        assertThat(source.toString(), instanceOf(String::class.java))
        assertThat(source.hashCode(), instanceOf(Int::class.java))
        assertEquals(source.name, "bbc")
        val article =
            Article(
                source,
                "jane doe",
                "title",
                "description",
                "url",
                "urltoImage",
                "2019-01-01"
            )
        assertEquals(article.source, source)
        assertEquals(article.describeContents(), 0)
        assertEquals(article.author, "jane doe")
        assertEquals(article.title, "title")
        assertEquals(article.description, "description")
        assertEquals(article.url, "url")
        assertEquals(article.urlToImage, "urltoImage")
        assertEquals(article.publishedAt, "2019-01-01")

        val newsReport = NewsReport("ok", 1L, listOf(article))
        val newsReport2 = NewsReport("ok", 1L, listOf(article))
        assertEquals(newsReport == newsReport2, true)
        assertEquals(newsReport.status, "ok")
        assertEquals(newsReport.totalResults, 1L)
        assertEquals(newsReport.articles[0], article)
        assertEquals(newsReport.copy("cancel", 1L, listOf(article)).status, "cancel")
        assertThat(newsReport.hashCode(), instanceOf(Int::class.java))
        assertThat(newsReport.toString(), instanceOf(String::class.java))
    }

    @Test
    fun test_fragmentTag() {
        val fragmentTag = FragmentTag(GeneralFragment(), "generalFragment")
        val fragmentTag2 = FragmentTag(GeneralFragment(), "generalFragment")
        assertThat(fragmentTag.toString(), instanceOf(String::class.java))
        assertEquals(fragmentTag == fragmentTag2, false)
        assertEquals(fragmentTag.tag, "generalFragment")
        assertEquals(fragmentTag.copy(Fragment(), "fragment").tag, "fragment")
        assertThat(fragmentTag.hashCode(), instanceOf(Int::class.java))
        assertThat(fragmentTag.fragment, instanceOf(Fragment::class.java))
    }
}

