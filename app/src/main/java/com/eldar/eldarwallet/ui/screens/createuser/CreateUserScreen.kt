package com.eldar.eldarwallet.ui.screens.createuser

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import com.eldar.eldarwallet.R
import com.eldar.eldarwallet.framework.ui.viewmodel.MainViewModel
import com.eldar.eldarwallet.ui.screens.common.AppBarComponent
import com.eldar.eldarwallet.ui.screens.common.FieldComponent
import com.eldar.eldarwallet.ui.screens.common.RectangleButtonComponent
import com.eldar.eldarwallet.ui.theme.backBtnLogin
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateUserScreen(
    viewModel: MainViewModel,
    onUpClick: () -> Unit
) {
    val name by viewModel.nameTextValue.collectAsState()
    val surname by viewModel.surnameTextValue.collectAsState()
    val email by viewModel.emailTextValue.collectAsState()
    val password by viewModel.passwordTextValue.collectAsState()
    val repeatPassword by viewModel.repeatPasswordTextValue.collectAsState()

    val focusRequester = FocusRequester()
    val snackbarMessage by viewModel.showSnackbarEvent.collectAsState(null)
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(snackbarMessage) {
        if (snackbarMessage != null) {
            scope.launch {
                snackbarHostState
                    .showSnackbar(
                        message = snackbarMessage!!,
                        duration = SnackbarDuration.Short,
                        withDismissAction = true
                    )
            }

            viewModel.showSnackbar(null)
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        topBar = {
            AppBarComponent(
                title = "Crear usuario",
                onUpStatus = true,
                onUpClick = {
                    onUpClick()
                },
                onCloseStatus = false,
                onCloseClick = {}
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.Start,
            content = {
                items(1) {
                    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.height_16)))

                    FieldComponent(
                        titleIsVisible = true,
                        title = stringResource(id = R.string.create_user_name),
                        inputKeyBoardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                        inputVisualTransformation = VisualTransformation.None,
                        keyboardActions = KeyboardActions(onNext = {
                            focusRequester.requestFocus()
                        }),
                        textFieldValue = name,
                        textFieldValueChange = { newText ->
                            viewModel.setNameTextValue(newText)
                        },
                        inputLabel = stringResource(id = R.string.create_user_name_hint)
                    )

                    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.height_16)))

                    FieldComponent(
                        titleIsVisible = true,
                        title = stringResource(id = R.string.create_user_surname),
                        inputKeyBoardOptions = KeyboardOptions(
                            imeAction = ImeAction.Done
                        ),
                        inputVisualTransformation = VisualTransformation.None,
                        keyboardActions = KeyboardActions(onNext = {
                            focusRequester.requestFocus()
                        }),
                        textFieldValue = surname,
                        textFieldValueChange = { newText ->
                            viewModel.setSurnameTextValue(newText)
                        },
                        inputLabel = stringResource(id = R.string.create_user_surname_hint)
                    )

                    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.height_16)))

                    FieldComponent(
                        titleIsVisible = true,
                        title = stringResource(id = R.string.login_email),
                        inputKeyBoardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                        inputVisualTransformation = VisualTransformation.None,
                        keyboardActions = KeyboardActions(onNext = {
                            focusRequester.requestFocus()
                        }),
                        textFieldValue = email,
                        textFieldValueChange = { newText ->
                            viewModel.setEmailTextValue(newText)
                        },
                        inputLabel = stringResource(id = R.string.login_email_hint)
                    )

                    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.height_16)))

                    FieldComponent(
                        titleIsVisible = true,
                        title = stringResource(id = R.string.login_password),
                        inputKeyBoardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                        inputVisualTransformation = VisualTransformation.None,
                        keyboardActions = KeyboardActions(onNext = {
                            focusRequester.requestFocus()
                        }),
                        textFieldValue = password,
                        textFieldValueChange = { newText ->
                            viewModel.setPasswordTextValue(newText)
                        },
                        inputLabel = stringResource(id = R.string.login_password_hint)
                    )

                    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.height_16)))

                    FieldComponent(
                        titleIsVisible = true,
                        title = stringResource(id = R.string.login_password),
                        inputKeyBoardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                        inputVisualTransformation = VisualTransformation.None,
                        keyboardActions = KeyboardActions(onNext = {
                            focusRequester.requestFocus()
                        }),
                        textFieldValue = repeatPassword,
                        textFieldValueChange = { newText ->
                            viewModel.setRepeatPasswordTextValue(newText)
                        },
                        inputLabel = stringResource(id = R.string.create_user_repeat_password_hint)
                    )

                    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.height_36)))

                    RectangleButtonComponent(
                        backColor = backBtnLogin,
                        label = stringResource(id = R.string.create_user_btn),
                        enabledStatus = true
                    ) {
                        viewModel.checkCreateUserFields()
                    }
                }
            }
        )
    }
}