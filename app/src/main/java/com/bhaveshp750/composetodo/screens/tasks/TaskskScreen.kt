package com.bhaveshp750.composetodo.screens.tasks

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bhaveshp750.composetodo.common.composable.ActionToolbar
import com.bhaveshp750.composetodo.common.ext.smallSpacer
import com.bhaveshp750.composetodo.common.ext.toolbarActions
import com.bhaveshp750.composetodo.R.string as AppText
import com.bhaveshp750.composetodo.R.drawable as AppIcon

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun TasksScreen(
  openScreen: (String) -> Unit,
  modifier: Modifier = Modifier,
  viewModel: TasksViewModel = hiltViewModel()
) {
  Scaffold(
    floatingActionButton = {
      FloatingActionButton(
        onClick = { viewModel.onAddClick(openScreen) },
        backgroundColor = MaterialTheme.colors.primary,
        contentColor = MaterialTheme.colors.onPrimary,
        modifier = modifier.padding((16.dp))
      ) {
        Icon(imageVector = Icons.Filled.Add, contentDescription = "Add")
      }
    }
  ) {
    val tasks = viewModel.tasks.collectAsStateWithLifecycle(emptyList())
    val options by viewModel.options
    
    Column(modifier = Modifier
      .fillMaxWidth()
      .fillMaxHeight()) {
      ActionToolbar(
        title = AppText.tasks,
        endActionIcon = AppIcon.ic_settings,
        modifier = Modifier.toolbarActions(),
        endAction = { viewModel.onSettingsClick(openScreen) }
      )

      Spacer(modifier = Modifier.smallSpacer())

      LazyColumn {
        items(tasks.value, key = {it.id}) { taskItem ->
          TaskItem(
            task = taskItem,
            options = options,
            onCheckChange = { viewModel.onTaskCheckChange(taskItem) },
            onActionClick = { action -> viewModel.onTaskActionClick(openScreen, taskItem, action) }
          )
        }
      }
    }
  }

  LaunchedEffect(viewModel) { viewModel.loadTaskOptions() }
}