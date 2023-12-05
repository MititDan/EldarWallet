package com.eldar.eldarwallet.ui.screens.paymentcompleted

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.eldar.eldarwallet.R

@Composable
fun PaymentCompletedScreen() {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(painter = painterResource(id = R.drawable.check_ic), contentDescription = null)
            
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.height_36)))
            
            Text(
                text = stringResource(id = R.string.payment_completed_info),
                fontSize = 18.sp,
                textAlign = TextAlign.Center
            )
        }
}