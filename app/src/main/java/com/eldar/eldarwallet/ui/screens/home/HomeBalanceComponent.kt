package com.eldar.eldarwallet.ui.screens.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.eldar.eldarwallet.R
import com.eldar.eldarwallet.framework.ui.viewmodel.MainViewModel

@Composable
fun HomeBalanceComponent(
    viewModel: MainViewModel,
) {
    var isBalanceVisible by remember { mutableStateOf(true) }
    val balance by viewModel.balanceTextValue.collectAsState()

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen.padding_16)),
        elevation = CardDefaults.cardElevation(4.dp),
        border = BorderStroke(1.dp, Color.LightGray),
        colors = CardDefaults.cardColors(
            containerColor = Color.LightGray
        )
    ) {
        Text(
            modifier = Modifier
                .padding(
                    start = dimensionResource(id = R.dimen.padding_16),
                    top = dimensionResource(id = R.dimen.padding_8)
                ),
            text = stringResource(id = R.string.home_balance_title),
            fontSize = 18.sp
        )

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.height_8)))

        Row() {
            Text(
                modifier = Modifier
                    .padding(
                        start = dimensionResource(id = R.dimen.padding_16),
                        bottom = dimensionResource(id = R.dimen.padding_8)
                    ),
                text = "$",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            if (isBalanceVisible) {
                Text(
                    modifier = Modifier
                        .padding(
                            start = dimensionResource(id = R.dimen.padding_4),
                            bottom = dimensionResource(id = R.dimen.padding_8)
                        ),
                    text = balance,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            } else {
                val asterisks = "*".repeat(balance.length)
                Text(
                    modifier = Modifier
                        .padding(
                            start = dimensionResource(id = R.dimen.padding_4),
                            bottom = dimensionResource(id = R.dimen.padding_8)
                        ),
                    text = asterisks,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            IconButton(
                onClick = {
                    isBalanceVisible = !isBalanceVisible
                },
                modifier = Modifier
                    .padding(
                        start = dimensionResource(id = R.dimen.padding_8),
                        top = dimensionResource(id = R.dimen.padding_5),
                        bottom = dimensionResource(id = R.dimen.padding_8)
                    )
                    .size(dimensionResource(id = R.dimen.size_24))
            ) {
                Image(
                    painter = if (isBalanceVisible) painterResource(id = R.drawable.visibility_ic) else painterResource(
                        id = R.drawable.visibility_off_ic
                    ),
                    contentDescription = null
                )
            }
        }
    }
}