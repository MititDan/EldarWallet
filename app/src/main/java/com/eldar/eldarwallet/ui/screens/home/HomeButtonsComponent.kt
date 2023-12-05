package com.eldar.eldarwallet.ui.screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.eldar.eldarwallet.R
import com.eldar.eldarwallet.ui.screens.common.RectangleButtonComponent
import com.eldar.eldarwallet.ui.theme.backBtnLogin
import com.eldar.eldarwallet.ui.theme.backQrFab

@Composable
fun HomeButtonsComponent(
    newBankCardOnClick: () -> Unit,
    generateQrOnClick: () -> Unit
) {
    RectangleButtonComponent(
        backColor = backBtnLogin,
        label = stringResource(id = R.string.home_new_card_btn),
        enabledStatus = true
    ) {
        newBankCardOnClick()
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {

        FloatingActionButton(
            modifier = Modifier
                .padding(
                    bottom = dimensionResource(id = R.dimen.padding_8)
                )
                .align(Alignment.BottomCenter),
            elevation = FloatingActionButtonDefaults.elevation(dimensionResource(id = R.dimen.elevation_8)),
            shape = CutCornerShape(dimensionResource(id = R.dimen.corner_shape_16)),
            containerColor = backQrFab,
            contentColor = Color.White,
            onClick = {
                generateQrOnClick()
            }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.qr_ic),
                contentDescription = null
            )
        }
    }
}