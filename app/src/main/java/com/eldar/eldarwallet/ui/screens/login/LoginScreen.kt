package com.eldar.eldarwallet.ui.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.eldar.eldarwallet.R
import com.eldar.eldarwallet.framework.ui.viewmodel.MainViewModel
import com.eldar.eldarwallet.ui.screens.common.AppBarComponent
import com.eldar.eldarwallet.ui.screens.common.RectangleButtonComponent
import com.eldar.eldarwallet.ui.screens.common.FieldComponent
import com.eldar.eldarwallet.ui.theme.backBtnLogin
import kotlinx.coroutines.launch

@Preview(showBackground = true, widthDp = 500, heightDp = 800)
@Composable
fun LoginScreenPreview() {

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    viewModel: MainViewModel,
    onClickCreateUser: () -> Unit
) {
    val email by viewModel.emailTextValue.collectAsState()
    val password by viewModel.passwordTextValue.collectAsState()

    val focusRequester = FocusRequester()
    val focusManager = LocalFocusManager.current
    var passwordVisible by rememberSaveable { mutableStateOf(false) }

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
                title = stringResource(id = R.string.app_name),
                onUpStatus = false,
                onUpClick = {},
                onCloseStatus = false,
                onCloseClick = {}
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start,
            content = {
                items(1) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .wrapContentSize(Alignment.Center)
                            .padding(top = dimensionResource(id = R.dimen.padding_24))
                            .size(110.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.wallet),
                            contentDescription = null
                        )
                    }

                    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.height_48)))

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

                    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.height_24)))

                    FieldComponent(
                        titleIsVisible = true,
                        title = stringResource(id = R.string.login_password),
                        inputKeyBoardOptions = KeyboardOptions(
                            imeAction = ImeAction.Done,
                            keyboardType = KeyboardType.Password
                        ),
                        inputVisualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        keyboardActions = KeyboardActions(onDone = {
                            focusManager.clearFocus()
                        }),
                        trailingIcon = if (passwordVisible) painterResource(id = R.drawable.visibility_ic) else painterResource(
                            id = R.drawable.visibility_off_ic
                        ),
                        onClickTrailing = {
                            passwordVisible = !passwordVisible
                        },

                        textFieldValue = password,
                        textFieldValueChange = { newText ->
                            viewModel.setPasswordTextValue(newText)
                        },
                        inputLabel = stringResource(id = R.string.login_password_hint)
                    )

                    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.height_36)))

                    RectangleButtonComponent(
                        backColor = backBtnLogin,
                        label = stringResource(id = R.string.login_entry_btn),
                        enabledStatus = true
                    ) {
                        viewModel.checkLoginFields()
                    }

                    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.height_36)))

                    RectangleButtonComponent(
                        backColor = backBtnLogin,
                        label = stringResource(id = R.string.login_create_new_user_btn),
                        enabledStatus = true
                    ) {
                        onClickCreateUser()
                    }
                }
            }
        )
    }
}