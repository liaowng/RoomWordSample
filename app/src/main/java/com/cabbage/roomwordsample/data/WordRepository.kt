package com.cabbage.roomwordsample.data

import androidx.lifecycle.LiveData
import com.cabbage.roomwordsample.room.Word
import com.cabbage.roomwordsample.room.WordDao

class WordRepository(private val wordDao: WordDao) {

    val allWords: LiveData<List<Word>> = wordDao.getAllWords()

    suspend fun insert(word: Word) {
        wordDao.insert(word)
    }
}