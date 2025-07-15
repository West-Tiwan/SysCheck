package com.example.syscheck.ui.screen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.syscheck.data.local.dao.TaskDAO
import com.example.syscheck.data.local.entity.TaskEntity
import com.example.syscheck.data.repository.TaskRepository
import com.example.syscheck.ui.viewmodel.TaskViewModel

class TaskListActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val taskViewModel: TaskViewModel = viewModel()
            TaskList(taskViewModel)
        }
    }
}

@Composable
fun TaskList(viewModel: TaskViewModel) {
    val tasks by viewModel.tasks.observeAsState(emptyList())

    val insertTask = TaskEntity (
        id = 0,
        title = "",
        desc = ""
    )

    Column {
        OutlinedTextField(
            value = insertTask.title,
            onValueChange = {insertTask.title = it}
        )
        OutlinedTextField(
            value = insertTask.desc,
            onValueChange = {insertTask.desc = it}
        )
        Button(onClick = { viewModel.insertTask(insertTask) }) {
            Text("Add Task")
        }
        LazyColumn {
            items(tasks) { task ->
                Text(text = task.title)
            }
        }
    }
}