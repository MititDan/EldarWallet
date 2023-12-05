package com.eldar.eldarwallet.ui.screens.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.eldar.eldarwallet.R
import com.eldar.eldarwallet.domain.BankCard
import com.eldar.eldarwallet.framework.common.getLastFourNumber
import com.eldar.eldarwallet.ui.theme.backAmericanExpress
import com.eldar.eldarwallet.ui.theme.backMastercard
import com.eldar.eldarwallet.ui.theme.backVisa
import com.eldar.eldarwallet.utils.Constants

@Composable
fun HomeBankCardItemComponent(
    item: BankCard,
    clickIsVisible: Boolean,
    onClickItem: () -> Unit
) {
    val background: Color? = when(item.type) {
        Constants.americanExpressType -> backAmericanExpress
        Constants.visaType -> backVisa
        Constants.mastercardType -> backMastercard
        else -> null
    }

    val icon: Int? = when(item.type) {
        Constants.americanExpressType -> R.drawable.american_express
        Constants.visaType -> R.drawable.visa
        Constants.mastercardType -> R.drawable.mastercard
        else -> null
    }

    val lastFourDigits = getLastFourNumber(item.cardNumber)

    if (background != null && icon != null) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.padding_16))
                .height(dimensionResource(id = R.dimen.height_80)),
            //elevation = CardDefaults.cardElevation(4.dp),
            border = BorderStroke(1.dp, Color.LightGray),
            colors = CardDefaults.cardColors(
                containerColor = Color.LightGray
            )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(background)
            ) {
                Box(
                    modifier = Modifier
                        .padding(
                            start = dimensionResource(id = R.dimen.padding_16),
                            bottom = dimensionResource(id = R.dimen.padding_16)
                        )
                        .background(
                            color = Color.DarkGray,
                            shape = RoundedCornerShape(
                                dimensionResource(id = R.dimen.shape_8)
                            )
                        )
                        .align(Alignment.BottomStart)
                ) {
                    Row() {
                        Text(
                            modifier = Modifier
                                .padding(
                                    top = dimensionResource(id = R.dimen.padding_6),
                                    start = dimensionResource(id = R.dimen.padding_4),
                                    bottom = dimensionResource(id = R.dimen.padding_4)
                                ),
                            text = "****",
                            color = Color.White
                        )

                        Text(
                            modifier = Modifier
                                .padding(
                                    top = dimensionResource(id = R.dimen.padding_4),
                                    end = dimensionResource(id = R.dimen.padding_4),
                                    bottom = dimensionResource(id = R.dimen.padding_4)
                                ),
                            text = lastFourDigits,
                            color = Color.White
                        )
                    }
                }

                Image(
                    modifier = Modifier
                        .padding(
                            top = dimensionResource(id = R.dimen.padding_18),
                            end = dimensionResource(id = R.dimen.padding_24)
                        )
                        .size(80.dp)
                        .align(Alignment.BottomEnd),
                    painter = painterResource(id = icon),
                    contentDescription = null
                )

                if (clickIsVisible) {
                    IconButton(
                        onClick = {
                            onClickItem()
                        },
                        modifier = Modifier
                            .padding(
                                end = dimensionResource(id = R.dimen.padding_8),
                                bottom = dimensionResource(id = R.dimen.padding_20)
                            )
                            .size(dimensionResource(id = R.dimen.size_24))
                            .align(Alignment.BottomEnd)
                    ) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowRight,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                }
            }
        }
    }
}