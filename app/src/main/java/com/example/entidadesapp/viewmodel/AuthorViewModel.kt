package com.example.entidadesapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.entidadesapp.data.AuthorDao
import com.example.entidadesapp.data.AuthorEntity
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AuthorViewModel(private val authorDao: AuthorDao) : ViewModel() {

    val authors: StateFlow<List<AuthorEntity>> = authorDao.getAllAuthors()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun addAuthor(name: String) {
        viewModelScope.launch {
            authorDao.insertAuthor(AuthorEntity(name = name))
        }
    }

    fun updateAuthor(author: AuthorEntity) {
        viewModelScope.launch {
            authorDao.updateAuthor(author)
        }
    }

    fun deleteAuthor(author: AuthorEntity) {
        viewModelScope.launch {
            authorDao.deleteAuthor(author)
        }
    }
}
