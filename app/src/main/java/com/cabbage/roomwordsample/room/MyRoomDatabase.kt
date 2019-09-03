package com.cabbage.roomwordsample.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Database(entities = [Word::class], version = 1)
abstract class MyRoomDatabase : RoomDatabase() {

    abstract fun wordDao(): WordDao

    companion object {

        @Volatile
        private var INSTANCE: MyRoomDatabase? = null

        @Synchronized
        fun getInstance(
            context: Context,
            scope: CoroutineScope
        ): MyRoomDatabase {

            return when (val instance = INSTANCE) {
                null -> {
                    Room.databaseBuilder(
                        context.applicationContext,
                        MyRoomDatabase::class.java,
                        "room_sample_database"
                    ).addCallback(MyDatabaseCallback(scope))
                        .build().also { INSTANCE = it }
                }
                else -> instance
            }
        }
    }

    private class MyDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {


        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { roomDb ->
                scope.launch { populateDatabase(roomDb.wordDao()) }
            }
        }


        private suspend fun populateDatabase(wordDao: WordDao) {
            wordDao.deleteAll()

            // Add sample words.
            var word = Word("Hello")
            wordDao.insert(word)
            word = Word("World!")
            wordDao.insert(word)
        }
    }
}