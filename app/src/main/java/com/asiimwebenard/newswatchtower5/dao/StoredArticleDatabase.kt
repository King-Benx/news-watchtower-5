package com.asiimwebenard.newswatchtower5.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.asiimwebenard.newswatchtower5.R

/**
 * The database class.
 */
@Database(entities = [StoredArticle::class], version = 1)
abstract class StoredArticleDatabase : RoomDatabase() {
    /**
     * Existent Daos in the application
     */
    abstract fun storedArticleDao(): StoredArticleDao

    companion object {
        /**
         * The database instance
         */
        private var storedArticleDatabase: StoredArticleDatabase? = null

        /**
         * Access to the database
         */
        fun getDatabase(context: Context): StoredArticleDatabase? {
            if (storedArticleDatabase == null) {
                synchronized(StoredArticleDatabase::class.java) {
                    if (storedArticleDatabase == null) {
                        this.storedArticleDatabase = Room.databaseBuilder<StoredArticleDatabase>(
                            context.applicationContext,
                            StoredArticleDatabase::class.java,
                            context.getString(R.string.article_database)
                        ).build()
                    }
                }
            }
            return storedArticleDatabase
        }
    }
}
