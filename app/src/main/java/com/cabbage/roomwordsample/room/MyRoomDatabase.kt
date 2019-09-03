package com.cabbage.roomwordsample.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Word::class], version = 1)
abstract class MyRoomDatabase : RoomDatabase() {
    
    abstract fun wordDao(): WordDao

    companion object {

        @Volatile
        private lateinit var INSTANCE: MyRoomDatabase

        @Synchronized
        fun getInstance(context: Context): MyRoomDatabase {

            if (!(::INSTANCE.isInitialized)) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MyRoomDatabase::class.java,
                    "room_sample_database"
                ).build()

                INSTANCE = instance
            }

            return INSTANCE
        }
    }
}