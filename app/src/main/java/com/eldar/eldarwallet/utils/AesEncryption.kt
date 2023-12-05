package com.eldar.eldarwallet.utils

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import java.security.Key
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec

object AesEncryption {
    private val keyAlias = "ASDfgh1234"
    private val keyStoreType = "AndroidKeyStore"
    private val keyAlgorithm = KeyProperties.KEY_ALGORITHM_AES
    private val blockMode = KeyProperties.BLOCK_MODE_CBC
    private val paddingScheme = KeyProperties.ENCRYPTION_PADDING_PKCS7
    private val keySize = 128

    fun generateKeyIfNotExists() {
        val existingKey = getKey()
        existingKey ?: generateKey()
    }

    private fun generateKey() {
        val keyGenerator = KeyGenerator.getInstance(keyAlgorithm, keyStoreType)
        keyGenerator.init(
            KeyGenParameterSpec.Builder(keyAlias, KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT)
                .setBlockModes(blockMode)
                .setEncryptionPaddings(paddingScheme)
                .setKeySize(keySize)
                .build()
        )

        keyGenerator.generateKey()
    }

    fun getKey(): SecretKey? {
        val keyStore = KeyStore.getInstance(keyStoreType)
        keyStore.load(null)
        return keyStore.getKey(keyAlias, null) as? SecretKey
    }

    fun encrypt(data: String, key: Key): String {
        val cipher = Cipher.getInstance("$keyAlgorithm/$blockMode/$paddingScheme")
        cipher.init(Cipher.ENCRYPT_MODE, key)

        val encryptedBytes = cipher.doFinal(data.toByteArray())
        val iv = cipher.iv

        return "${Base64.encodeToString(iv, Base64.DEFAULT)}:${Base64.encodeToString(encryptedBytes, Base64.DEFAULT)}"
    }

    fun decrypt(encryptedData: String, key: Key): String {
        val parts = encryptedData.split(":")
        if (parts.size != 2) {
            throw IllegalArgumentException("Invalid encrypted data format")
        }

        val iv = Base64.decode(parts[0], Base64.DEFAULT)
        val encryptedBytes = Base64.decode(parts[1], Base64.DEFAULT)

        val cipher = Cipher.getInstance("$keyAlgorithm/$blockMode/$paddingScheme")
        cipher.init(Cipher.DECRYPT_MODE, key, IvParameterSpec(iv))

        val decryptedBytes = cipher.doFinal(encryptedBytes)
        return String(decryptedBytes)
    }
}