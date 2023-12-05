package com.eldar.eldarwallet.ui.screens.common

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.sp
import com.eldar.eldarwallet.R
import com.eldar.eldarwallet.ui.theme.btnInputBack
import com.eldar.eldarwallet.ui.theme.inputHint

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FieldComponent(
    titleIsVisible: Boolean,
    title: String? = null,
    inputKeyBoardOptions: KeyboardOptions? = null,
    inputVisualTransformation: VisualTransformation? = null,
    keyboardActions: KeyboardActions? = null,
    trailingIcon: Painter? = null,
    onClickTrailing: () -> Unit = {},
    textFieldValue: String,
    textFieldValueChange: (String) -> Unit,
    inputLabel: String
) {
    if (titleIsVisible) {
        Text(
            modifier = Modifier
                .padding(
                    start = dimensionResource(id = R.dimen.padding_16)
                ),
            text = title ?: "",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.height_8)))
    }

    TextField(
        value = textFieldValue,
        onValueChange = {
            textFieldValueChange(it)
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = dimensionResource(id = R.dimen.padding_16),
                end = dimensionResource(id = R.dimen.padding_16)
            )
            .border(
                dimensionResource(id = R.dimen.border_1), btnInputBack,
                shape = RoundedCornerShape(dimensionResource(id = R.dimen.shape_4))
            ),

        placeholder = {
            Text(
                text = inputLabel,
                fontSize = 14.sp,
                color = inputHint
            )
        },
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.Black,
            containerColor = Color.White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        keyboardOptions = inputKeyBoardOptions ?: KeyboardOptions.Default,
        visualTransformation = inputVisualTransformation ?: VisualTransformation.None,
        keyboardActions = keyboardActions ?: KeyboardActions.Default,
        trailingIcon = {
            if (trailingIcon != null) {
                IconButton(
                    onClick = {
                        onClickTrailing()
                    }
                ) {
                    Icon(
                        painter = trailingIcon,
                        contentDescription = null
                    )
                }
            }
        }
    )
}