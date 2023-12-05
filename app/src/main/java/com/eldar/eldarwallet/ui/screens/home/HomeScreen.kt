package com.eldar.eldarwallet.ui.screens.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.eldar.eldarwallet.R
import com.eldar.eldarwallet.domain.BankCard
import com.eldar.eldarwallet.framework.ui.viewmodel.MainViewModel
import com.eldar.eldarwallet.ui.screens.common.AppBarComponent
import com.eldar.eldarwallet.utils.DatastoreManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: MainViewModel,
    newBankCardOnClick: () -> Unit,
    generateQrOnClick: () -> Unit,
    onClickCard: (BankCard) -> Unit,
    onLogoutClick: () -> Unit
) {
    val cardsList by viewModel.cardsListValue.collectAsState()

    Scaffold(
        topBar = {
            AppBarComponent(
                title = stringResource(id = R.string.app_name),
                onUpStatus = false,
                onUpClick = {},
                onCloseStatus = true,
                onCloseClick = {
                    onLogoutClick()
                }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
        ) {
            HomeBalanceComponent(
                viewModel = viewModel
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = dimensionResource(id = R.dimen.padding_8),
                        bottom = dimensionResource(id = R.dimen.padding_16),
                        start = dimensionResource(id = R.dimen.padding_16),
                        end = dimensionResource(id = R.dimen.padding_16)
                    )
                    .weight(1f),
                elevation = CardDefaults.cardElevation(4.dp),
                border = BorderStroke(1.dp, Color.LightGray),
                colors = CardDefaults.cardColors(
                    containerColor = Color.LightGray
                )
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = dimensionResource(id = R.dimen.padding_8)),
                    content = {
                        items(
                            cardsList
                        ) { item ->
                            HomeBankCardItemComponent(
                                item = item,
                                clickIsVisible = true,
                                onClickItem = {
                                    onClickCard(item)
                                }
                            )
                        }
                    }
                )

            }

            HomeButtonsComponent(
                newBankCardOnClick = {
                    newBankCardOnClick()
                },
                generateQrOnClick = {
                    generateQrOnClick()
                }
            )
        }
    }

    DisposableEffect(true) {
        viewModel.setBalanceTextValue("25.000")
        viewModel.loadCards()
        onDispose {  }
    }
}


