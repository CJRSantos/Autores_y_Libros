package com.example.entidadesapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.entidadesapp.data.BookDao
import com.example.entidadesapp.data.BookEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class BookViewModel(private val bookDao: BookDao) : ViewModel() {

    private val _authorId = MutableStateFlow<Int?>(null)
    
    val books: StateFlow<List<BookEntity>> = _authorId
        .flatMapLatest { id ->
            if (id != null) bookDao.getBooksByAuthor(id)
            else kotlinx.coroutines.flow.flowOf(emptyList())
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun setAuthorId(id: Int) {
        _authorId.value = id
    }

    fun addBook(title: String, authorId: Int) {
        viewModelScope.launch {
            bookDao.insertBook(BookEntity(title = title, authorId = authorId))
        }
    }

    fun deleteBook(book: BookEntity) {
        viewModelScope.launch {
            bookDao.deleteBook(book)
        }
    }
}
