package com.example.syscheck.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.syscheck.data.local.dao.TaskDAO
import com.example.syscheck.data.local.entity.TaskEntity

@Database(entities = [TaskEntity::class], version = 1, exportSchema = false)
abstract class TaskDatabase: RoomDatabase() {
    abstract fun taskDao(): TaskDAO

    object DatabaseProvider {
        fun provideDatabase(context: Context): TaskDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                TaskDatabase::class.java,
                "task_database"
            ).build()
        }
    }
}