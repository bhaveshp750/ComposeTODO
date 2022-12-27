package com.bhaveshp750.composetodo.common.composable

import androidx.annotation.StringRes
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp

@Composable
fun BasicTextButton(@StringRes text: Int, modifier: Modifier, action: () -> Unit) {
  TextButton(onClick = action, modifier = modifier) { Text(stringResource(text)) }
}

@Composable
fun BasicButton(@StringRes text: Int, modifier: Modifier, action: () -> Unit) {
  Button(
    onClick = action,
    modifier = modifier,
    colors =
      ButtonDefaults.buttonColors(
        backgroundColor = MaterialTheme.colors.primary,
        contentColor = MaterialTheme.colors.onPrimary,
      )
  ) {
    Text(stringResource(text), fontSize = 16.sp)
  }
}

@Composable
fun DialogConfirmButton(@StringRes text: Int, action: () -> Unit) {
  Button(
    onClick = action,
    colors =
    ButtonDefaults.buttonColors(
      backgroundColor = MaterialTheme.colors.primary,
      contentColor = MaterialTheme.colors.onPrimary
    )
  ) {
    Text(stringResource(text))
  }
}

@Composable
fun DialogCancelButton(@StringRes text: Int, action: () -> Unit) {
  Button(
    onClick = action,
    colors =
    ButtonDefaults.buttonColors(
      backgroundColor = MaterialTheme.colors.onPrimary,
      contentColor = MaterialTheme.colors.primary
    )
  ) {
    Text(stringResource(text))
  }
}
