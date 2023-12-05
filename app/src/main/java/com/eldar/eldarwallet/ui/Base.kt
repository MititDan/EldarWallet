package com.eldar.eldarwallet.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.eldar.eldarwallet.ui.theme.EldarWalletTheme

@Composable
fun Base(content: @Composable () -> Unit) {
    EldarWalletTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            content()
        }
    }
}