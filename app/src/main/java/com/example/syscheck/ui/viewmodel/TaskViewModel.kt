package com.example.syscheck.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.syscheck.SysCheckApplication
import com.example.syscheck.data.local.dao.TaskDAO
import com.example.syscheck.data.local.entity.TaskEntity
import kotlinx.coroutines.launch

class AndroidTaskViewModel(application: Application) : AndroidViewModel(application) {
    private val dao: TaskDAO by lazy {
        (application as SysCheckApplication).database.taskDao()
    }
    
    val tasks = dao.getTaskAll().asLiveData()

    fun insertTask(task: TaskEntity) = viewModelScope.launch {
        dao.insertTask(task)
    }
}