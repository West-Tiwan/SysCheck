package com.example.syscheck

import android.app.Application
import com.example.syscheck.data.local.database.TaskDatabase

class SysCheckApplication : Application() {
    
    val database by lazy { TaskDatabase.DatabaseProvider.provideDatabase(this) }
}
