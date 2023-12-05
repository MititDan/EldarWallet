package com.eldar.eldarwallet.ui.screens.payment

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.eldar.eldarwallet.R
import com.eldar.eldarwallet.domain.BankCard
import com.eldar.eldarwallet.framework.ui.viewmodel.MainViewModel
import com.eldar.eldarwallet.ui.screens.common.AppBarComponent
import com.eldar.eldarwallet.ui.screens.common.RectangleButtonComponent
import com.eldar.eldarwallet.ui.screens.home.HomeBankCardItemComponent
import com.eldar.eldarwallet.ui.theme.backBtnLogin
import com.eldar.eldarwallet.utils.Constants
import com.eldar.eldarwallet.utils.DatastoreManager
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GeneratePaymentScreen(
    type: String,
    cardNumber: String,
    viewModel: MainViewModel,
    onUpClick: () -> Unit,
    onClickSimulatePayment: () -> Unit
) {
    val snackbarMessage by viewModel.showSnackbarEvent.collectAsState(null)
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    val name = rememberSaveable {
        mutableStateOf("")
    }

    val surname = rememberSaveable {
        mutableStateOf("")
    }

    val context = LocalContext.current

    LaunchedEffect(true) {
        name.value = DatastoreManager.getData(
            context, Constants.keyUserName, ""
        ) ?: ""

        surname.value = DatastoreManager.getData(
            context, Constants.keyUserSurname, ""
        ) ?: ""
    }

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
                title = stringResource(id = R.string.generate_payment),
                onUpStatus = true,
                onUpClick = {
                    onUpClick()
                },
                onCloseStatus = false,
                onCloseClick = {}
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.height_16)))

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = dimensionResource(id = R.dimen.padding_8),
                        end = dimensionResource(id = R.dimen.padding_8)
                    ),
                text = "${name.value.uppercase()} ${surname.value.uppercase()}",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.height_16)))

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = dimensionResource(id = R.dimen.padding_8),
                        end = dimensionResource(id = R.dimen.padding_8)
                    ),
                text = stringResource(id = R.string.generate_payment_associated_card),
                color = Color.LightGray,
                fontSize = 18.sp,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.height_4)))

            HomeBankCardItemComponent(
                item = BankCard(
                    "", cardNumber, "", "", type
                ),
                clickIsVisible = false,
                onClickItem = {}
            )

            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.height_16)))

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = dimensionResource(id = R.dimen.padding_8),
                        end = dimensionResource(id = R.dimen.padding_8)
                    ),
                text = stringResource(id = R.string.generate_payment_available_balance),
                color = Color.LightGray,
                fontSize = 18.sp,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.height_8)))

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = dimensionResource(id = R.dimen.padding_8),
                        end = dimensionResource(id = R.dimen.padding_8)
                    ),
                text = "$25.000",
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.height_16)))

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = dimensionResource(id = R.dimen.padding_8),
                        end = dimensionResource(id = R.dimen.padding_8)
                    ),
                text = stringResource(id = R.string.generate_payment_info_label),
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                color = Color.LightGray
            )

            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.height_36)))

            RectangleButtonComponent(
                backColor = backBtnLogin,
                label = stringResource(id = R.string.generate_payment_simulate_payment),
                enabledStatus = true
            ) {
                onClickSimulatePayment()
            }
        }
    }
}