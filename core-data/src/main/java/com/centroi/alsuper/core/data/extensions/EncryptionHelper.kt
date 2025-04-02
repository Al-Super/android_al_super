package com.centroi.alsuper.core.data.extensions

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec
import android.util.Base64

object EncryptionHelper {
    private const val ANDROID_KEYSTORE = "AndroidKeyStore"
    private const val KEY_ALIAS = "EmergencyContactKey"
    private const val AES_MODE = "AES/GCM/NoPadding"
    private const val KEY_SIZE = 256
    private const val IV_SIZE = 12
    private const val T_LEN = 128

    // Generate or Retrieve Secret Key
    private fun getSecretKey(): SecretKey {
        val keyStore = KeyStore.getInstance(ANDROID_KEYSTORE).apply { load(null) }
        val existingKey = keyStore.getEntry(KEY_ALIAS, null) as? KeyStore.SecretKeyEntry
        if (existingKey != null) {
            return existingKey.secretKey
        }

        val keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, ANDROID_KEYSTORE)
        val spec = KeyGenParameterSpec.Builder(
            KEY_ALIAS,
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
        )
            .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
            .setKeySize(KEY_SIZE)
            .build()
        keyGenerator.init(spec)
        return keyGenerator.generateKey()
    }

    // Encrypt Data
    fun encrypt(data: String): String {
        val cipher = Cipher.getInstance(AES_MODE)
        cipher.init(Cipher.ENCRYPT_MODE, getSecretKey())

        val iv = cipher.iv // Get IV for decryption
        val encryptedData = cipher.doFinal(data.toByteArray())

        return Base64.encodeToString(iv + encryptedData, Base64.DEFAULT) // Store IV + CipherText
    }

    // Decrypt Data
    fun decrypt(encryptedData: String): String {
        val cipher = Cipher.getInstance(AES_MODE)
        val decodedData = Base64.decode(encryptedData, Base64.DEFAULT)

        val iv = decodedData.sliceArray(0 until IV_SIZE) // Extract IV (first 12 bytes)
        val encryptedBytes = decodedData.sliceArray(IV_SIZE until decodedData.size)

        val spec = GCMParameterSpec(T_LEN, iv)
        cipher.init(Cipher.DECRYPT_MODE, getSecretKey(), spec)

        return String(cipher.doFinal(encryptedBytes))
    }
}
