package com.example.entidadesapp.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.entidadesapp.data.AppDatabase
import com.example.entidadesapp.ui.AuthorsScreen
import com.example.entidadesapp.ui.BooksScreen
import com.example.entidadesapp.viewmodel.AuthorViewModel
import com.example.entidadesapp.viewmodel.BookViewModel
import com.example.entidadesapp.viewmodel.ViewModelFactory

@Composable
fun NavGraph(
    database: AppDatabase
) {
    val navController = rememberNavController()
    val authorViewModel: AuthorViewModel = viewModel(factory = ViewModelFactory(database))
    val bookViewModel: BookViewModel = viewModel(factory = ViewModelFactory(database))

    NavHost(navController = navController, startDestination = "authors") {
        composable("authors") {
            AuthorsScreen(authorViewModel) { authorId ->
                navController.navigate("books/$authorId")
            }
        }
        composable(
            "books/{authorId}",
            arguments = listOf(navArgument("authorId") { type = NavType.IntType })
        ) { backStackEntry ->
            val authorId = backStackEntry.arguments?.getInt("authorId") ?: 0
            BooksScreen(bookViewModel, authorId)
        }
    }
}
