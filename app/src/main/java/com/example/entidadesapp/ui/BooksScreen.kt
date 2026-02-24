package com.example.entidadesapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.entidadesapp.data.BookEntity
import com.example.entidadesapp.viewmodel.BookViewModel

@Composable
fun BookCard(
    bookName: String,
    authorName: String,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = bookName,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Autor: $authorName",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.DarkGray
                )
            }
            Row {
                IconButton(onClick = onEdit) {
                    Icon(Icons.Default.Edit, contentDescription = "Editar", tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f))
                }
                IconButton(onClick = onDelete) {
                    Icon(Icons.Default.Delete, contentDescription = "Borrar", tint = Color.Red.copy(alpha = 0.6f))
                }
            }
        }
    }
}

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
    
    // State for Add Dialog
    var showAddDialog by remember { mutableStateOf(false) }
    var newBookTitle by remember { mutableStateOf("") }

    // State for Edit Dialog
    var bookToEdit by remember { mutableStateOf<BookEntity?>(null) }
    var editedTitle by remember { mutableStateOf("") }

    // State for Delete Confirmation Dialog
    var bookToDelete by remember { mutableStateOf<BookEntity?>(null) }

    Scaffold(
        topBar = { NobleTopBar("Libros") },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddDialog = true },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = Color.White
            ) {
                Icon(Icons.Default.Add, contentDescription = "Añadir Libro")
            }
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        LazyColumn(
            modifier = Modifier.padding(padding).fillMaxSize(),
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            items(books) { book ->
                BookCard(
                    bookName = book.title,
                    authorName = "Autor ID: ${book.authorId}",
                    onEdit = {
                        bookToEdit = book
                        editedTitle = book.title
                    },
                    onDelete = { bookToDelete = book }
                )
            }
        }

        // Add Book Dialog
        if (showAddDialog) {
            AlertDialog(
                onDismissRequest = { showAddDialog = false },
                title = { Text("Nuevo Libro") },
                text = {
                    TextField(
                        value = newBookTitle,
                        onValueChange = { newBookTitle = it },
                        label = { Text("Título") },
                        singleLine = true
                    )
                },
                confirmButton = {
                    TextButton(onClick = {
                        if (newBookTitle.isNotBlank()) {
                            viewModel.addBook(newBookTitle, authorId)
                            newBookTitle = ""
                            showAddDialog = false
                        }
                    }) { Text("Añadir") }
                },
                dismissButton = {
                    TextButton(onClick = { showAddDialog = false }) { Text("Cancelar") }
                }
            )
        }

        // Edit Book Dialog
        bookToEdit?.let { book ->
            AlertDialog(
                onDismissRequest = { bookToEdit = null },
                title = { Text("Editar Libro") },
                text = {
                    TextField(
                        value = editedTitle,
                        onValueChange = { editedTitle = it },
                        label = { Text("Título") },
                        singleLine = true
                    )
                },
                confirmButton = {
                    TextButton(onClick = {
                        if (editedTitle.isNotBlank()) {
                            viewModel.updateBook(book.copy(title = editedTitle))
                            bookToEdit = null
                        }
                    }) { Text("Guardar") }
                },
                dismissButton = {
                    TextButton(onClick = { bookToEdit = null }) { Text("Cancelar") }
                }
            )
        }

        // Delete Confirmation Dialog
        bookToDelete?.let { book ->
            AlertDialog(
                onDismissRequest = { bookToDelete = null },
                title = { Text("Eliminar Libro") },
                text = { Text("¿Esta seguro de querer eliminar?") },
                confirmButton = {
                    TextButton(
                        onClick = {
                            viewModel.deleteBook(book)
                            bookToDelete = null
                        },
                        colors = ButtonDefaults.textButtonColors(contentColor = Color.Red)
                    ) { Text("Eliminar") }
                },
                dismissButton = {
                    TextButton(onClick = { bookToDelete = null }) { Text("Cancelar") }
                }
            )
        }
    }
}
