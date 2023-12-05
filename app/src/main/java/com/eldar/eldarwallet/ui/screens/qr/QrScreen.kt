package com.eldar.eldarwallet.ui.screens.qr

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.eldar.eldarwallet.R
import com.eldar.eldarwallet.framework.ui.viewmodel.MainViewModel
import com.eldar.eldarwallet.ui.screens.common.AppBarComponent
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QrScreen(
    viewModel: MainViewModel,
    onUpClick: () -> Unit
) {

    val qr by viewModel.qrValue.collectAsState()

    val snackbarMessage by viewModel.showSnackbarEvent.collectAsState(null)
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

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
                title = stringResource(id = R.string.generate_qr),
                onUpStatus = true,
                onUpClick = {
                    onUpClick()
                },
                onCloseStatus = false,
                onCloseClick = {}
            )
        }
    ) { padding ->
        qr?.let {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .background(Color.White),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(qr)
                        .crossfade(2000)
                        .build(),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize(),
                    contentScale = ContentScale.Fit
                )
            }
        }
    }

    DisposableEffect(true) {
        viewModel.generateQr()
        onDispose {  }
    }
}