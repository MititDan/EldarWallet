package com.eldar.eldarwallet.ui.screens.newbankcard

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalTextStyle
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
import com.eldar.eldarwallet.utils.DateVisualTransformation
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewBankCardScreen(
    viewModel: MainViewModel,
    onUpClick: () -> Unit
) {
    val fullName by viewModel.fullNameTextValue.collectAsState()
    val cardNumber by viewModel.cardNumberTextValue.collectAsState()
    val expDate by viewModel.expDateTextValue.collectAsState()
    val securityCode by viewModel.securityCodeTextValue.collectAsState()

    val focusRequester = FocusRequester()
    val visualTransformation = DateVisualTransformation()

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
                title = stringResource(id = R.string.new_bank_card),
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
                        title = stringResource(id = R.string.new_card_name_title),
                        inputKeyBoardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                        inputVisualTransformation = VisualTransformation.None,
                        keyboardActions = KeyboardActions(onNext = {
                            focusRequester.requestFocus()
                        }),
                        textFieldValue = fullName,
                        textFieldValueChange = { newText ->
                            viewModel.setFullNameTextValue(newText)
                        },
                        inputLabel = stringResource(id = R.string.new_card_name_hint)
                    )

                    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.height_16)))

                    FieldComponent(
                        titleIsVisible = true,
                        title = stringResource(id = R.string.new_card_card_number_title),
                        inputKeyBoardOptions = KeyboardOptions(
                            imeAction = ImeAction.Done,
                            keyboardType = KeyboardType.Number
                        ),
                        inputVisualTransformation = VisualTransformation.None,
                        keyboardActions = KeyboardActions(onNext = {
                            focusRequester.requestFocus()
                        }),
                        textFieldValue = cardNumber,
                        textFieldValueChange = { newText ->
                            viewModel.setCardNumberTextValue(newText)
                        },
                        inputLabel = stringResource(id = R.string.new_card_card_number_hint)
                    )

                    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.height_16)))

                    FieldComponent(
                        titleIsVisible = true,
                        title = stringResource(id = R.string.new_card_exp_date_title),
                        inputKeyBoardOptions = KeyboardOptions(
                            imeAction = ImeAction.Done,
                            keyboardType = KeyboardType.Number
                        ),
                        inputVisualTransformation = visualTransformation,
                        keyboardActions = KeyboardActions(onNext = {
                            focusRequester.requestFocus()
                        }),
                        textFieldValue = expDate,
                        textFieldValueChange = { newText ->
                            if (newText.length <= 4) {
                                viewModel.setExpDateTextValue(newText)
                            }
                        },
                        inputLabel = stringResource(id = R.string.new_card_exp_date_hint)
                    )

                    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.height_16)))

                    FieldComponent(
                        titleIsVisible = true,
                        title = stringResource(id = R.string.new_card_security_code_title),
                        inputKeyBoardOptions = KeyboardOptions(
                            imeAction = ImeAction.Done,
                            keyboardType = KeyboardType.Number
                        ),
                        inputVisualTransformation = VisualTransformation.None,
                        keyboardActions = KeyboardActions(onNext = {
                            focusRequester.requestFocus()
                        }),
                        textFieldValue = securityCode,
                        textFieldValueChange = { newText ->
                            viewModel.setSecurityCodeTextValue(newText)
                        },
                        inputLabel = stringResource(id = R.string.new_card_security_code_hint)
                    )

                    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.height_36)))

                    RectangleButtonComponent(
                        backColor = backBtnLogin,
                        label = stringResource(id = R.string.new_card_save_btn),
                        enabledStatus = true
                    ) {
                        viewModel.checkNewBankCardFields()
                    }
                }
            }
        )
    }
}