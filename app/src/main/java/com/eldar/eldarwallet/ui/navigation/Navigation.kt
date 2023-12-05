package com.eldar.eldarwallet.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.eldar.eldarwallet.framework.ui.viewmodel.MainViewModel
import com.eldar.eldarwallet.ui.screens.createuser.CreateUserScreen
import com.eldar.eldarwallet.ui.screens.home.HomeScreen
import com.eldar.eldarwallet.ui.screens.login.LoginScreen
import com.eldar.eldarwallet.ui.screens.newbankcard.NewBankCardScreen
import com.eldar.eldarwallet.ui.screens.payment.GeneratePaymentScreen
import com.eldar.eldarwallet.ui.screens.paymentcompleted.PaymentCompletedScreen
import com.eldar.eldarwallet.ui.screens.qr.QrScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavItem.Login.route
    ) {

        composable(
            route = NavItem.Login.route
        ) {
            val viewModel = hiltViewModel<MainViewModel>()
            viewModel.setNavController(navController)

            LoginScreen(
                viewModel = viewModel,
                onClickCreateUser = {
                    navController.navigate(NavItem.CreateUser.route)
                }
            )
        }

        composable(
            route = NavItem.CreateUser.route
        ) {
            val viewModel = hiltViewModel<MainViewModel>()
            viewModel.setNavController(navController)

            CreateUserScreen(
                viewModel = viewModel,
                onUpClick = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = NavItem.Home.route
        ) {
            val viewModel = hiltViewModel<MainViewModel>()
            viewModel.setNavController(navController)

            HomeScreen(
                viewModel = viewModel,
                newBankCardOnClick = {
                    navController.navigate(NavItem.NewBankCard.route)
                },
                generateQrOnClick = {
                    navController.navigate(NavItem.Qr.route)
                },
                onClickCard = { card ->
                    navController.navigate(NavItem.Payment.createNavRoute(
                        type = card.type, cardNumber = card.cardNumber
                    ))
                },
                onLogoutClick = {
                    viewModel.invokeClearDatastore()
                    navController.navigate(NavItem.Login.route) {
                        popUpTo(NavItem.Home.route) { inclusive = true }
                    }
                }
            )
        }

        composable(
            route = NavItem.NewBankCard.route
        ) {
            val viewModel = hiltViewModel<MainViewModel>()
            viewModel.setNavController(navController)

            NewBankCardScreen(
                viewModel = viewModel,
                onUpClick = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = NavItem.Qr.route
        ) {
            val viewModel = hiltViewModel<MainViewModel>()
            viewModel.setNavController(navController)

            QrScreen(
                viewModel = viewModel,
                onUpClick = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = NavItem.Payment.route,
            arguments = NavItem.Payment.args
        ) { backStackEntry ->

            val type = backStackEntry.arguments?.getString(NavArg.Type.key)
            val cardNumber = backStackEntry.arguments?.getString(NavArg.CardNumber.key)
            requireNotNull(type, { "type null" })
            requireNotNull(cardNumber, { "lastfourdigit null" })

            val viewModel = hiltViewModel<MainViewModel>()
            viewModel.setNavController(navController)

            GeneratePaymentScreen(
                type = type,
                cardNumber = cardNumber,
                viewModel = viewModel,
                onUpClick = {
                    navController.popBackStack()
                },
                onClickSimulatePayment = {
                    viewModel.simulatePayment()
                }
            )
        }

        composable(
            route = NavItem.PaymentCompleted.route
        ) {
            PaymentCompletedScreen()
        }
    }
}