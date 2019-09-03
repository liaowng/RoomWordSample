package com.cabbage.roomwordsample.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.cabbage.roomwordsample.data.WordRepository
import com.cabbage.roomwordsample.room.MyRoomDatabase
import com.cabbage.roomwordsample.room.Word
import kotlinx.coroutines.launch

class WordViewModel(app: Application) : AndroidViewModel(app) {

    private val repo: WordRepository

    val allWords: LiveData<List<Word>>

    init {
        val dao = MyRoomDatabase.getInstance(getApplication(), viewModelScope).wordDao()
        repo = WordRepository(dao)
        allWords = repo.allWords
    }

    fun insert(word: Word) = viewModelScope.launch {
        repo.insert(word)
    }
}