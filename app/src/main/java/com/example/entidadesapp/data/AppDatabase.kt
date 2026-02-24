package com.example.entidadesapp.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [AuthorEntity::class, BookEntity::class],
    version = 2
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun authorDao(): AuthorDao
    abstract fun bookDao(): BookDao
}
