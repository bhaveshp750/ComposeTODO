package com.bhaveshp750.composetodo.screens.edit_task

import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.bhaveshp750.composetodo.common.composable.*
import com.bhaveshp750.composetodo.common.ext.card
import com.bhaveshp750.composetodo.common.ext.fieldModifier
import com.bhaveshp750.composetodo.common.ext.spacer
import com.bhaveshp750.composetodo.common.ext.toolbarActions
import com.bhaveshp750.composetodo.model.Priority
import com.bhaveshp750.composetodo.model.Task
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.bhaveshp750.composetodo.R.string as AppText
import com.bhaveshp750.composetodo.R.drawable as AppIcon

@Composable
fun EditTaskScreen(
  popUpScreen: () -> Unit,
  taskId: String,
  modifier: Modifier = Modifier,
  viewModel: EditTaskViewModel = hiltViewModel()
) {
  val task by viewModel.task

  LaunchedEffect(Unit) { viewModel.initialize(taskId) }

  Column(
    modifier = modifier
      .fillMaxWidth()
      .fillMaxHeight()
      .verticalScroll(rememberScrollState()),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    ActionToolbar(
      title = AppText.edit_task,
      endActionIcon = AppIcon.ic_check,
      modifier = Modifier.toolbarActions(),
      endAction = { viewModel.onDoneClick(popUpScreen) }
    )

    Spacer(modifier = Modifier.spacer())

    val fieldModifier = Modifier.fieldModifier()
    BasicField(AppText.title, task.title, viewModel::onTitleChange, fieldModifier)
    BasicField(AppText.description, task.description, viewModel::onDescriptionChange, fieldModifier)
    BasicField(AppText.url, task.url, viewModel::onUrlChange, fieldModifier)

    Spacer(modifier = Modifier.spacer())
    CardEditors(task, viewModel::onDateChange, viewModel::onTimeChange)
    CardSelectors(task, viewModel::onPriorityChange, viewModel::onFlagToggle)

    Spacer(modifier = Modifier.spacer())
  }
}

@Composable
fun CardEditors(
  task: Task,
  onDateChange: (Long) -> Unit,
  onTimeChange: (Int, Int) -> Unit
) {
  val activity = LocalContext.current as AppCompatActivity

  RegularCardEditor(AppText.date, AppIcon.ic_calendar, task.dueDate, Modifier.card()) {
    showDatePicker(activity, onDateChange)
  }

  RegularCardEditor(AppText.time, AppIcon.ic_clock, task.dueTime, Modifier.card()) {
    showTimePicker(activity, onTimeChange)
  }
}

@Composable
fun CardSelectors(
  task: Task,
  onPriorityChange: (String) -> Unit,
  onFlagToggle: (String) -> Unit
) {
  val prioritySelection = Priority.getByName(task.priority).name
  CardSelector(AppText.priority, Priority.getOptions(), prioritySelection, Modifier.card()) { newValue ->
    onPriorityChange(newValue)
  }

  val flagSelector = EditFlagOption.getByCheckedState(task.flag).name
  CardSelector(AppText.flag, EditFlagOption.getOptions(), flagSelector, Modifier.card()) { newValue ->
    onFlagToggle(newValue)
  }
}

fun showDatePicker(activity: AppCompatActivity?, onDateChange: (Long) -> Unit) {
  val picker = MaterialDatePicker.Builder.datePicker().build()

  activity?.let {
    picker.show(it.supportFragmentManager, picker.toString())
    picker.addOnPositiveButtonClickListener { timeInMillis -> onDateChange(timeInMillis) }
  }
}

fun showTimePicker(activity: AppCompatActivity?, onTimeChange: (Int, Int) -> Unit) {
  val picker = MaterialTimePicker.Builder().setTimeFormat(TimeFormat.CLOCK_24H).build()

  activity?.let {
    picker.show(it.supportFragmentManager, picker.toString())
    picker.addOnPositiveButtonClickListener { onTimeChange(picker.hour, picker.minute) }
  }
}