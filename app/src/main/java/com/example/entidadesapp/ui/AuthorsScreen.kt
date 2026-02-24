package com.example.entidadesapp.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.entidadesapp.data.AuthorEntity
import com.example.entidadesapp.viewmodel.AuthorViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthorsScreen(
    viewModel: AuthorViewModel,
    onAuthorClick: (Int) -> Unit
) {
    val authors by viewModel.authors.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    var authorName by remember { mutableStateOf("") }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Autores") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = { showDialog = true }) {
                Icon(Icons.Default.Add, contentDescription = "Añadir Autor")
            }
        }
    ) { padding ->
        LazyColumn(modifier = Modifier.padding(padding)) {
            items(authors) { author ->
                ListItem(
                    headlineContent = { Text(author.name) },
                    modifier = Modifier.clickable { onAuthorClick(author.id) },
                    trailingContent = {
                        IconButton(onClick = { viewModel.deleteAuthor(author) }) {
                            Icon(Icons.Default.Delete, contentDescription = "Borrar")
                        }
                    }
                )
            }
        }

        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text("Nuevo Autor") },
                text = {
                    TextField(
                        value = authorName,
                        onValueChange = { authorName = it },
                        label = { Text("Nombre") }
                    )
                },
                confirmButton = {
                    TextButton(onClick = {
                        if (authorName.isNotBlank()) {
                            viewModel.addAuthor(authorName)
                            authorName = ""
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
