package com.asiimwebenard.newswatchtower5

import android.content.res.Resources
import androidx.fragment.app.Fragment
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.asiimwebenard.newswatchtower5.models.Article
import com.asiimwebenard.newswatchtower5.models.Source
import com.asiimwebenard.newswatchtower5.newsviews.InternationalNewsFragment
import com.asiimwebenard.newswatchtower5.newsviews.LocaleNewsFragment
import com.asiimwebenard.newswatchtower5.shared.GeneralFragment
import com.asiimwebenard.newswatchtower5.shared.fragments
import com.asiimwebenard.newswatchtower5.shared.loadFragment
import com.asiimwebenard.newswatchtower5.shared.mFragments
import org.hamcrest.Matchers
import org.hamcrest.Matchers.not
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class NewsActivityTest {
    private lateinit var resources: Resources

    @Rule
    @JvmField
    var activityTestRule: ActivityTestRule<NewsActivity> =
        ActivityTestRule(NewsActivity::class.java)

    private fun useFragment(fragment: Pair<String, Fragment>) {
        loadFragment(
            activityTestRule.activity.supportFragmentManager,
            activityTestRule.activity.findViewById(R.id.frame),
            fragment
        )
    }

    @Before
    fun setUp() {
        resources = activityTestRule.activity.resources
    }

    @After
    fun tearDown() {
        mFragments.clear()
        fragments.clear()
    }

    @Test
    fun testYourSavedStoriesFragmentIsDisplayed() {
        activityTestRule.activity.loadSavedArticles()

        onView(withId(R.id.title)).check(
            matches(withText(R.string.saved_articles))
        )

        onView(withId(R.id.home_button)).check(
            matches(withContentDescription(R.string.home_button))
        )


        Thread.sleep(5000)
    }

    @Test
    fun testDetailViewIsDisplayed() {

        val article = Article(
            Source(null, "TechCrunch"),
            "Natasha Lomas",
            "Europe agrees platform rules to tackle unfair business practices",
            "The European Union’s political institutions have reached agreement " +
                    "over new rules designed to boost transparency around online platform " +
                    "businesses and curb unfair practices to support traders and other businesses " +
                    "that rely on digital intermediaries for discov…",
            "http://techcrunch.com/2019/02/14/europe-agrees-platform-rules-to-tackle-" +
                    "unfair-business-practices/",
            "https://techcrunch.com/wp-content/uploads/2015/01/mobilecommerce.jpg?w=533",
            "2019-02-14T11:13:14Z"
        )

        activityTestRule.activity.loadDetailView(article)


        onView(withId(R.id.title)).check(
            matches(withText(R.string.news_details))
        )

        onView(withId(R.id.home_button)).check(
            matches(withContentDescription(R.string.home_button))
        )

        val headlineTextView = onView(
            Matchers.allOf(
                withId(R.id.headlineTextView),
                isDisplayed()
            )
        )
        headlineTextView.check(matches(isDisplayed()))
        headlineTextView.check(matches(withText(article.title)))

        val descriptionTextView = onView(
            Matchers.allOf(
                withId(R.id.descriptionTextView),
                isDisplayed()
            )
        )
        descriptionTextView.check(matches(isDisplayed()))
        descriptionTextView.check(matches(withText(article.description)))

        onView(withId(R.id.floatingActionShareButton)).check(matches(not(isDisplayed())))

        onView(withId(R.id.sourceUrlTextView)).check(matches(not(isDisplayed())))

        onView(withId(R.id.authorTextView)).check(matches(not(isDisplayed())))

        onView(withId(R.id.sourceTextView)).check(matches(not(isDisplayed())))

        onView(withId(R.id.floatingActionSaveButton)).check(matches(not(isDisplayed())))

        onView(withId(R.id.publishDateTextView)).check(matches(isDisplayed()))

        onView(withContentDescription(resources.getString(R.string.news_image_description))).check(
            matches(isDisplayed())
        )

        onView(withText(resources.getString(R.string.author))).check(matches(not(isDisplayed())))

        onView(withText(resources.getString(R.string.source))).check(matches(not(isDisplayed())))

        onView(withText(resources.getString(R.string.publish_date))).check(matches(isDisplayed()))

        onView(withId(R.id.floatingActionSaveButton)).perform(scrollTo())
            .check(matches(isDisplayed())).check(matches(isClickable()))

        onView(withId(R.id.floatingActionShareButton)).check(matches(isDisplayed()))
            .check(matches(isClickable()))

        onView(withText(resources.getString(R.string.author))).check(matches(isDisplayed()))

        onView(withText(resources.getString(R.string.source))).check(matches(isDisplayed()))

        onView(withText(resources.getString(R.string.publish_date))).check(matches(isDisplayed()))

        onView(withId(R.id.sourceUrlTextView)).check(matches(isDisplayed()))
            .check(matches(isClickable()))

        onView(withId(R.id.authorTextView)).check(matches(isDisplayed()))

        onView(withId(R.id.sourceTextView)).check(matches(isDisplayed()))

        Thread.sleep(5000)
    }

    @Test
    fun testGeneralFragmentIsDisplayed() {
        val fragment =
            Pair(resources.getString(R.string.generalFragment), GeneralFragment())

        useFragment(fragment)
        val frameLayout = onView(
            Matchers.allOf(
                withId(R.id.recycler_view),
                isDisplayed()
            )
        )
        frameLayout.check(matches(isDisplayed()))
        Thread.sleep(5000)
    }

    @Test
    fun testLocaleNewsFragmentIsDisplayed() {
        val fragment =
            Pair(resources.getString(R.string.localeNewsFragment), LocaleNewsFragment())

        useFragment(fragment)

        onView(withId(R.id.title)).check(
            matches(withText(R.string.other_locale_news))
        )

        onView(withId(R.id.home_button)).check(
            matches(withContentDescription(R.string.home_button))
        )

        val frameLayout = onView(
            Matchers.allOf(
                withId(R.id.recycler_view),
                isDisplayed()
            )
        )
        frameLayout.check(matches(isDisplayed()))

        val spinnerView = onView(Matchers.allOf(withId(R.id.spinner), isDisplayed(), isClickable()))
        spinnerView.check(matches(isDisplayed()))
        spinnerView.perform(click())

        Thread.sleep(5000)
    }

    @Test
    fun testInternationalNewsFragmentIsDisplayed() {
        val fragment =
            Pair(resources.getString(R.string.internationalFragment), InternationalNewsFragment())

        useFragment(fragment)

        onView(withId(R.id.title)).check(
            matches(withText(R.string.top_headlines))
        )

        onView(withId(R.id.home_button)).check(
            matches(withContentDescription(R.string.home_button))
        )

        val frameLayout = onView(
            Matchers.allOf(
                withId(R.id.recycler_view),
                isDisplayed()
            )
        )
        frameLayout.check(matches(isDisplayed()))
        Thread.sleep(5000)
    }
}
