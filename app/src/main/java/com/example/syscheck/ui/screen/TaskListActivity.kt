package com.example.syscheck.ui.screen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.syscheck.SysCheckApplication
import com.example.syscheck.data.local.entity.TaskEntity
import com.example.syscheck.ui.viewmodel.AndroidTaskViewModel

class TaskListActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                val taskViewModel: AndroidTaskViewModel = viewModel()
                TaskListWithAndroidViewModel(taskViewModel)
            }
        }
    }
}

@Composable
fun TaskListWithAndroidViewModel(viewModel: AndroidTaskViewModel) {
    val tasks by viewModel.tasks.observeAsState(emptyList())
    
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Task Title") },
            modifier = Modifier.fillMaxWidth()
        )
        
        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Task Description") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        )
        
        Button(
            onClick = {
                if (title.isNotBlank()) {
                    val newTask = TaskEntity(
                        id = 0,
                        title = title,
                        desc = description
                    )
                    viewModel.insertTask(newTask)
                    title = ""
                    description = ""
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text("Add Task")
        }
        
        LazyColumn(
            modifier = Modifier.padding(top = 16.dp)
        ) {
            items(tasks) { task ->
                TaskItem(task = task)
            }
        }
    }
}

@Composable
fun TaskItem(task: TaskEntity) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = task.title,
                style = MaterialTheme.typography.headlineSmall
            )
            if (task.desc.isNotBlank()) {
                Text(
                    text = task.desc,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}