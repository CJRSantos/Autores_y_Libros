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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.entidadesapp.data.CategoryEntity
import com.example.entidadesapp.viewmodel.CategoryViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoriesScreen(
    viewModel: CategoryViewModel,
    onCategoryClick: (Int) -> Unit
) {
    val categories by viewModel.categories.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    var categoryName by remember { mutableStateOf("") }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Categorías") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = { showDialog = true }) {
                Icon(Icons.Default.Add, contentDescription = "Añadir Categoría")
            }
        }
    ) { padding ->
        LazyColumn(modifier = Modifier.padding(padding)) {
            items(categories) { category ->
                ListItem(
                    headlineContent = { Text(category.name) },
                    modifier = Modifier.clickable { onCategoryClick(category.id) },
                    trailingContent = {
                        IconButton(onClick = { viewModel.deleteCategory(category) }) {
                            Icon(Icons.Default.Delete, contentDescription = "Borrar")
                        }
                    }
                )
            }
        }

        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text("Nueva Categoría") },
                text = {
                    TextField(
                        value = categoryName,
                        onValueChange = { categoryName = it },
                        label = { Text("Nombre") }
                    )
                },
                confirmButton = {
                    TextButton(onClick = {
                        if (categoryName.isNotBlank()) {
                            viewModel.addCategory(categoryName)
                            categoryName = ""
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
