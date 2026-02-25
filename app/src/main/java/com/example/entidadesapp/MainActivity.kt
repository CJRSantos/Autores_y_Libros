package com.example.entidadesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.room.Room
import com.example.entidadesapp.data.AppDatabase
import com.example.entidadesapp.navigation.NavGraph
import com.example.entidadesapp.ui.theme.EntidadesAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializa la base de datos
        // Usamos fallbackToDestructiveMigration() para evitar crashes si el esquema cambia durante el desarrollo
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "entidades-db"
        ).fallbackToDestructiveMigration()
         .build()

        setContent {
            EntidadesAppTheme {
                // Llama al NavGraph y pasa la base de datos
                NavGraph(database = db)
            }
        }
    }
}