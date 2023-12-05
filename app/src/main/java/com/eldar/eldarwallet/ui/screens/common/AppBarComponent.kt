package com.eldar.eldarwallet.ui.screens.common

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import com.eldar.eldarwallet.R
import com.eldar.eldarwallet.ui.theme.backPrimaryColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBarComponent(
    title: String,
    onUpStatus: Boolean,
    onUpClick: () -> Unit,
    onCloseStatus: Boolean,
    onCloseClick: () -> Unit
) {
    TopAppBar(
        title = {
                Text(
                    text = title
                )
        },
        actions = {
            if (onCloseStatus) {
                AppBarAction(imageVector = Icons.Default.Close,
                    onClick = {
                        onCloseClick()
                    })
            }
        },
        navigationIcon = {
            if (onUpStatus) {
                ArrowBackIcon(onUpClick = onUpClick)
            }
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = backPrimaryColor,
            titleContentColor = Color.White,
            actionIconContentColor = Color.White,
            navigationIconContentColor = Color.White
        )
    )
}

@Composable
fun AppBarAction(imageVector: ImageVector, onClick: () -> Unit) {
    IconButton(onClick = { onClick() }) {
        Icon(imageVector = imageVector,
            contentDescription = null)
    }
}

@Composable
private fun ArrowBackIcon(onUpClick: () -> Unit) {
    IconButton(onClick = onUpClick) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = null
        )
    }
}