package com.example.entidadesapp.ui

import androidx.compose.foundation.clickable
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
import com.example.entidadesapp.data.AuthorEntity
import com.example.entidadesapp.viewmodel.AuthorViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NobleTopBar(title: String) {
    TopAppBar(
        title = {
            Text(
                text = title,
                fontWeight = FontWeight.SemiBold
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = Color.White
        )
    )
}

@Composable
fun AuthorCard(
    authorName: String,
    onClick: () -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { onClick() },
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
                    text = authorName,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "Autor registrado",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
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

@Composable
fun AuthorsScreen(
    viewModel: AuthorViewModel,
    onAuthorClick: (Int) -> Unit
) {
    val authors by viewModel.authors.collectAsState()
    
    // State for Add Dialog
    var showAddDialog by remember { mutableStateOf(false) }
    var newAuthorName by remember { mutableStateOf("") }

    // State for Edit Dialog
    var authorToEdit by remember { mutableStateOf<AuthorEntity?>(null) }
    var editedName by remember { mutableStateOf("") }

    // State for Delete Confirmation Dialog
    var authorToDelete by remember { mutableStateOf<AuthorEntity?>(null) }

    Scaffold(
        topBar = { NobleTopBar("Autores") },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddDialog = true },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = Color.White
            ) {
                Icon(Icons.Default.Add, contentDescription = "Añadir Autor")
            }
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        LazyColumn(
            modifier = Modifier.padding(padding).fillMaxSize(),
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            items(authors) { author ->
                AuthorCard(
                    authorName = author.name,
                    onClick = { onAuthorClick(author.id) },
                    onEdit = {
                        authorToEdit = author
                        editedName = author.name
                    },
                    onDelete = { authorToDelete = author }
                )
            }
        }

        // Add Author Dialog
        if (showAddDialog) {
            AlertDialog(
                onDismissRequest = { showAddDialog = false },
                title = { Text("Nuevo Autor") },
                text = {
                    TextField(
                        value = newAuthorName,
                        onValueChange = { newAuthorName = it },
                        label = { Text("Nombre") },
                        singleLine = true
                    )
                },
                confirmButton = {
                    TextButton(onClick = {
                        if (newAuthorName.isNotBlank()) {
                            viewModel.addAuthor(newAuthorName)
                            newAuthorName = ""
                            showAddDialog = false
                        }
                    }) { Text("Añadir") }
                },
                dismissButton = {
                    TextButton(onClick = { showAddDialog = false }) { Text("Cancelar") }
                }
            )
        }

        // Edit Author Dialog
        authorToEdit?.let { author ->
            AlertDialog(
                onDismissRequest = { authorToEdit = null },
                title = { Text("Editar Autor") },
                text = {
                    TextField(
                        value = editedName,
                        onValueChange = { editedName = it },
                        label = { Text("Nombre") },
                        singleLine = true
                    )
                },
                confirmButton = {
                    TextButton(onClick = {
                        if (editedName.isNotBlank()) {
                            viewModel.updateAuthor(author.copy(name = editedName))
                            authorToEdit = null
                        }
                    }) { Text("Guardar") }
                },
                dismissButton = {
                    TextButton(onClick = { authorToEdit = null }) { Text("Cancelar") }
                }
            )
        }

        // Delete Confirmation Dialog
        authorToDelete?.let { author ->
            AlertDialog(
                onDismissRequest = { authorToDelete = null },
                title = { Text("Eliminar Autor") },
                text = { Text("¿Esta seguro de querer eliminar?") },
                confirmButton = {
                    TextButton(
                        onClick = {
                            viewModel.deleteAuthor(author)
                            authorToDelete = null
                        },
                        colors = ButtonDefaults.textButtonColors(contentColor = Color.Red)
                    ) { Text("Eliminar") }
                },
                dismissButton = {
                    TextButton(onClick = { authorToDelete = null }) { Text("Cancelar") }
                }
            )
        }
    }
}
