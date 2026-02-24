package com.example.entidadesapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.entidadesapp.viewmodel.BookViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BooksScreen(
    viewModel: BookViewModel,
    authorId: Int
) {
    LaunchedEffect(authorId) {
        viewModel.setAuthorId(authorId)
    }

    val books by viewModel.books.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    var bookTitle by remember { mutableStateOf("") }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Libros") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = { showDialog = true }) {
                Icon(Icons.Default.Add, contentDescription = "Añadir Libro")
            }
        }
    ) { padding ->
        LazyColumn(modifier = Modifier.padding(padding)) {
            items(books) { book ->
                ListItem(
                    headlineContent = { Text(book.title) },
                    trailingContent = {
                        IconButton(onClick = { viewModel.deleteBook(book) }) {
                            Icon(Icons.Default.Delete, contentDescription = "Borrar")
                        }
                    }
                )
            }
        }

        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text("Nuevo Libro") },
                text = {
                    TextField(
                        value = bookTitle,
                        onValueChange = { bookTitle = it },
                        label = { Text("Título") }
                    )
                },
                confirmButton = {
                    TextButton(onClick = {
                        if (bookTitle.isNotBlank()) {
                            viewModel.addBook(bookTitle, authorId)
                            bookTitle = ""
                            showDialog = false
                        }
                    }) { Text("Añadir") }
                },
                dismissButton = {
                    TextButton(onClick = { showDialog = false }) { Text("Cancelar") }
                }
            )
        }
    }
}
