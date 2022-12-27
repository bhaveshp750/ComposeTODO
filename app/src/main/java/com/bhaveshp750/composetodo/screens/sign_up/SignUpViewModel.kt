package com.bhaveshp750.composetodo.screens.sign_up

import androidx.compose.runtime.mutableStateOf
import com.bhaveshp750.composetodo.SETTINGS_SCREEN
import com.bhaveshp750.composetodo.SIGN_UP_SCREEN
import com.bhaveshp750.composetodo.common.ext.isValidEmail
import com.bhaveshp750.composetodo.common.ext.isValidPassword
import com.bhaveshp750.composetodo.common.ext.passwordMatches
import com.bhaveshp750.composetodo.common.snackbar.SnackbarManager
import com.bhaveshp750.composetodo.model.service.AccountService
import com.bhaveshp750.composetodo.model.service.LogService
import com.bhaveshp750.composetodo.screens.login.ComposeTODOViewModel
import com.bhaveshp750.composetodo.R.string as AppText
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
  private val accountService: AccountService,
  logService: LogService
) : ComposeTODOViewModel(logService) {
  var uiState = mutableStateOf(SignUpUiState())
    private set

  private val email
    get() = uiState.value.email
  private val password
    get() = uiState.value.password

  fun onEmailChange(newValue: String) {
    uiState.value = uiState.value.copy(email = newValue)
  }

  fun onPasswordChange(newValue: String) {
    uiState.value = uiState.value.copy(password = newValue)
  }

  fun onRepeatPasswordChange(newValue: String) {
    uiState.value = uiState.value.copy(repeatPassword = newValue)
  }

  fun onSignUpClick(openAndPopUp: (String, String) -> Unit) {
    if (!email.isValidEmail()) {
      SnackbarManager.showMessage(AppText.email_error)
      return
    }

    if (!password.isValidPassword()) {
      SnackbarManager.showMessage(AppText.password_error)
      return
    }

    if (!password.passwordMatches(uiState.value.repeatPassword)) {
      SnackbarManager.showMessage(AppText.password_match_error)
      return
    }

    launchCatching {
      accountService.linkAccount(email, password)
      openAndPopUp(SETTINGS_SCREEN, SIGN_UP_SCREEN)
    }
  }
}