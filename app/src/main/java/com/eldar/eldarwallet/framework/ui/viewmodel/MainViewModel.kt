package com.eldar.eldarwallet.framework.ui.viewmodel

import android.app.Application
import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.eldar.eldarwallet.R
import com.eldar.eldarwallet.data.repository.CardsRepo
import com.eldar.eldarwallet.data.repository.GenerateQrRepo
import com.eldar.eldarwallet.data.repository.UsersRepo
import com.eldar.eldarwallet.domain.BankCard
import com.eldar.eldarwallet.domain.User
import com.eldar.eldarwallet.framework.common.checkValidEmail
import com.eldar.eldarwallet.framework.common.checkValidPassword
import com.eldar.eldarwallet.framework.common.getFirstNumber
import com.eldar.eldarwallet.ui.navigation.NavItem
import com.eldar.eldarwallet.utils.CheckPing
import com.eldar.eldarwallet.utils.Constants
import com.eldar.eldarwallet.utils.DatastoreManager
import com.eldar.eldarwallet.utils.MyLogger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val application: Application,
    private val cardsRepo: CardsRepo,
    private val generateQrRepo: GenerateQrRepo,
    private val usersRepo: UsersRepo
) : ViewModel() {

    //login screen / create user
    private val _emailTextValue = MutableStateFlow("")
    val emailTextValue = _emailTextValue.asStateFlow()
    fun setEmailTextValue(email: String) {
        _emailTextValue.value = email
    }

    private val _passwordTextValue = MutableStateFlow("")
    val passwordTextValue = _passwordTextValue.asStateFlow()
    fun setPasswordTextValue(password: String) {
        _passwordTextValue.value = password
    }

    private val _repeatPasswordTextValue = MutableStateFlow("")
    val repeatPasswordTextValue = _repeatPasswordTextValue.asStateFlow()
    fun setRepeatPasswordTextValue(repeatPassword: String) {
        _repeatPasswordTextValue.value = repeatPassword
    }

    private val _nameTextValue = MutableStateFlow("")
    val nameTextValue = _nameTextValue.asStateFlow()
    fun setNameTextValue(name: String) {
        _nameTextValue.value = name
    }

    private val _surnameTextValue = MutableStateFlow("")
    val surnameTextValue = _surnameTextValue.asStateFlow()
    fun setSurnameTextValue(surname: String) {
        _surnameTextValue.value = surname
    }

    //home screen
    private val _balanceTextValue = MutableStateFlow("")
    val balanceTextValue = _balanceTextValue.asStateFlow()
    fun setBalanceTextValue(balance: String) {
        _balanceTextValue.value = balance
    }

    private val _cardsListValue = MutableStateFlow(listOf<BankCard>())
    val cardsListValue = _cardsListValue.asStateFlow()
    fun setCardsListValue(list: List<BankCard>) {
        _cardsListValue.value = list
    }

    //new bank card screen
    private val _fullNameTextValue = MutableStateFlow("")
    val fullNameTextValue = _fullNameTextValue.asStateFlow()
    fun setFullNameTextValue(fullName: String) {
        _fullNameTextValue.value = fullName
    }

    private val _cardNumberTextValue = MutableStateFlow("")
    val cardNumberTextValue = _cardNumberTextValue.asStateFlow()
    fun setCardNumberTextValue(cardNumber: String) {
        _cardNumberTextValue.value = cardNumber
    }

    private val _expDateTextValue = MutableStateFlow("")
    val expDateTextValue = _expDateTextValue.asStateFlow()
    fun setExpDateTextValue(expDate: String) {
        _expDateTextValue.value = expDate
    }

    private val _securityCodeTextValue = MutableStateFlow("")
    val securityCodeTextValue = _securityCodeTextValue.asStateFlow()
    fun setSecurityCodeTextValue(securityCode: String) {
        _securityCodeTextValue.value = securityCode
    }

    //qr screen
    private val _qrValue = MutableStateFlow<Bitmap?>(null)
    val qrValue = _qrValue.asStateFlow()
    fun setQrValue(qr: Bitmap?) {
        _qrValue.value = qr
    }

    //navegacion
    private var navController: NavHostController? = null

    fun setNavController(controller: NavHostController) {
        navController = controller
    }

    //snackbar
    private val _showSnackbarEvent = MutableSharedFlow<String?>()
    val showSnackbarEvent: SharedFlow<String?> = _showSnackbarEvent

    fun showSnackbar(message: String?) {
        viewModelScope.launch {
            _showSnackbarEvent.emit(message)
        }
    }

    //limpio nombre y apellido de usuario en datastore al cerrar sesion
    fun invokeClearDatastore() {
        try {
            viewModelScope.launch {
                DatastoreManager.clearDatastore(application)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(Constants.tagDebug, "catch invokeClearDatastore: ${e.message}")
        }
    }

    //cargo las tarjetas en el home
    fun loadCards() {
        try {
            viewModelScope.launch {
                val cards = cardsRepo.getAllCards()

                setCardsListValue(cards)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            MyLogger.logError("catch loadCards: ${e.message}")
        }
    }

    //envio nombre y apellido de usuario a la api para generar QR
    fun generateQr() {
        try {
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    if (CheckPing.isInternetAvailable("rapidapi.com")) {
                        val userName = DatastoreManager.getData(
                            application, Constants.keyUserName, ""
                        )

                        val userSurname = DatastoreManager.getData(
                            application, Constants.keyUserSurname, ""
                        )

                        Log.i(Constants.tagDebug, "nombre y apellido enviado a api qr: $userName $userSurname")
                        val image = generateQrRepo.generateQr("$userName $userSurname")

                        image?.let {
                            setQrValue(image)

                        } ?: run {
                            showSnackbar(application.getString(R.string.snackbar_qr_error))
                        }

                    } else {
                        showSnackbar(application.getString(R.string.snackbar_connection_error))
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(Constants.tagDebug, "catch generateQr: ${e.message}")
        }
    }

    //login
    fun checkLoginFields() {
        try {
            viewModelScope.launch {
                val email = emailTextValue.value
                val password = passwordTextValue.value

                if (email.isNotEmpty() && password.isNotEmpty()) {
                    viewModelScope.launch {
                        //obtengo la tabla usuarios
                        val loginStatus = usersRepo.loginUser(email, password)

                        if (loginStatus) {
                            /*setEmailTextValue("")
                            setPasswordTextValue("")*/

                            navController?.navigate(NavItem.Home.route) {
                                popUpTo(NavItem.Login.route) { inclusive = true }
                            }

                        } else {
                            //email o contraseña incorrecto
                            showSnackbar(application.getString(R.string.snackbar_user_not_found))
                        }
                    }
                } else {
                    //campos vacios
                    showSnackbar(application.getString(R.string.snackbar_emptys_fields))
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            MyLogger.logError("catch checkLoginFields: ${e.message}")
        }
    }

    //crear usuario
    fun checkCreateUserFields() {
        try {
            val name = nameTextValue.value
            val surname = surnameTextValue.value
            val email = emailTextValue.value
            val password = passwordTextValue.value
            val repeatPassword = repeatPasswordTextValue.value

            if (name.isNotEmpty() && surname.isNotEmpty() && email.isNotEmpty() &&
                    password.isNotEmpty() && repeatPassword.isNotEmpty()) {

                if (checkValidEmail(email)) {
                    if (checkValidPassword(password)) {
                        if (password == repeatPassword) {
                            //registrar usuario
                            viewModelScope.launch {
                                val result = usersRepo.saveNewUser(User(
                                        name = name,
                                        surname = surname,
                                        email = email,
                                        password = password
                                    ))

                                if (result != 0L) {
                                    setNameTextValue("")
                                    setSurnameTextValue("")
                                    setEmailTextValue("")
                                    setPasswordTextValue("")
                                    setRepeatPasswordTextValue("")

                                    showSnackbar(application.getString(R.string.snackbar_saved_user))

                                } else {
                                    showSnackbar(application.getString(R.string.snackbar_error_saving_user))
                                }
                            }
                        } else {
                            //las contrasenas no son iguales
                            showSnackbar(application.getString(R.string.snackbar_passwords_not_match))
                        }
                    } else {
                        //formato de contrasena invalido
                        showSnackbar(application.getString(R.string.snackbar_invalid_password))
                    }
                } else {
                    //email invalido
                    showSnackbar(application.getString(R.string.snackbar_invalid_email))
                }
            } else {
                showSnackbar(application.getString(R.string.snackbar_emptys_fields))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            MyLogger.logError("catch checkCreateUserFields: ${e.message}")
        }
    }

    //agregar tarjeta
    fun checkNewBankCardFields() {
        try {
            val fullName = fullNameTextValue.value
            val cardNumber = cardNumberTextValue.value
            val expDate = expDateTextValue.value
            val securityCode = securityCodeTextValue.value

            if (
                fullName.isNotEmpty() && cardNumber.isNotEmpty()
                && expDate.isNotEmpty() && securityCode.isNotEmpty()
            ) {
                viewModelScope.launch {
                        var type: String? = null
                        val firstNumber = getFirstNumber(cardNumber)

                        firstNumber?.let {
                            if (firstNumber == 3) {
                                type = Constants.americanExpressType
                            } else if (firstNumber == 4) {
                                type = Constants.visaType
                            } else if (firstNumber == 5) {
                                type = Constants.mastercardType
                            }

                            if (type != null) {
                                val result = cardsRepo.saveNewCard(
                                    BankCard(
                                        fullName = fullName,
                                        cardNumber = cardNumber,
                                        expDate = expDate,
                                        securityCode = securityCode,
                                        type = type!!
                                    )
                                )

                                result?.let {
                                    if (result != 0L) {
                                        setFullNameTextValue("")
                                        setCardNumberTextValue("")
                                        setExpDateTextValue("")
                                        setSecurityCodeTextValue("")

                                        showSnackbar(application.getString(R.string.snackbar_saved_card))

                                    } else {
                                        showSnackbar(application.getString(R.string.snackbar_error_saving_card))
                                    }

                                } ?: run {
                                    //la tarjeta no pertenece al usuario actual
                                    showSnackbar(application.getString(R.string.snackbar_invalid_user))
                                }
                            } else {
                                showSnackbar(application.getString(R.string.snackbar_invalid_card))
                            }
                        } ?: run {
                            showSnackbar(application.getString(R.string.snackbar_error_saving_card))
                        }
                }
            } else {
                showSnackbar(application.getString(R.string.snackbar_emptys_fields))
            }

        } catch (e: Exception) {
            e.printStackTrace()
            MyLogger.logError("catch checkNewBankCardFields: ${e.message}")
        }
    }

    fun simulatePayment() {
        try {
            viewModelScope.launch {
                navController?.navigate(NavItem.PaymentCompleted.route)

                delay(2500)

                navController?.navigate(NavItem.Home.route) {
                    popUpTo(NavItem.PaymentCompleted.route) { inclusive = true }
                    popUpTo(NavItem.Payment.route) { inclusive = true }
                    popUpTo(NavItem.Home.route) { inclusive = true }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(Constants.tagDebug, "catch simulate payment: ${e.message}")
        }
    }
}