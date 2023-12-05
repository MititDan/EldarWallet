package com.eldar.eldarwallet.framework.common

import org.junit.Assert
import org.junit.Test

class ExtensionKtTest {

    @Test
    fun invalidEmail1ReturnFalse() {
        val result = checkValidEmail(
            "daniel.com"
        )

        Assert.assertFalse(result)
    }

    @Test
    fun invalidEmail2ReturnFalse() {
        val result = checkValidEmail(
            "daniel@.com"
        )

        Assert.assertFalse(result)
    }

    @Test
    fun invalidEmail3ReturnFalse() {
        val result = checkValidEmail(
            "@gmail.com"
        )

        Assert.assertFalse(result)
    }

    @Test
    fun validEmailReturnTrue() {
        val result = checkValidEmail(
            "daniel@gmail.com"
        )

        Assert.assertTrue(result)
    }

    @Test
    fun checkInvalidPassword1ReturnFalse() {
        val result = checkValidPassword(
            "dani"
        )

        Assert.assertFalse(result)
    }

    @Test
    fun checkInvalidPassword2ReturnFalse() {
        val result = checkValidPassword(
            "dani123"
        )

        Assert.assertFalse(result)
    }

    @Test
    fun checkInvalidPassword3ReturnFalse() {
        val result = checkValidPassword(
            "Dani"
        )

        Assert.assertFalse(result)
    }

    @Test
    fun checkValidPasswordReturnTrue() {
        val result = checkValidPassword(
            "Dani1234"
        )

        Assert.assertTrue(result)
    }
}