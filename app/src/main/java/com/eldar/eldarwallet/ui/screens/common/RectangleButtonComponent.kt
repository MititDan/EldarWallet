package com.eldar.eldarwallet.ui.screens.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.sp
import com.eldar.eldarwallet.R

@Composable
fun RectangleButtonComponent(
    backColor: Color, label: String, enabledStatus: Boolean, onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = dimensionResource(id = R.dimen.padding_16),
                end = dimensionResource(id = R.dimen.padding_16),
                bottom = dimensionResource(id = R.dimen.padding_16)
            )
            .background(
                color = backColor,
                shape = RoundedCornerShape(dimensionResource(id = R.dimen.shape_8))
            )
            .clickable(
                enabled = enabledStatus,
                onClick = {
                    onClick()
                }
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier
                .padding(
                    top = dimensionResource(id = R.dimen.padding_16),
                    bottom = dimensionResource(id = R.dimen.padding_16)
                ),
            text = label,
            color = Color.White,
            fontSize = 18.sp
        )
    }
}