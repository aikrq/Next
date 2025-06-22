package ru.aikrq.next.core.util

import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

object SketchwareEncryptor {
    private val encryptKey = "sketchwaresecure".toByteArray()

    fun ByteArray.decrypt(): ByteArray {
        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        cipher.init(2, SecretKeySpec(encryptKey, "AES"), IvParameterSpec(encryptKey))
        return cipher.doFinal(this@decrypt) ?: error("Error while decrypting string.")
    }

    fun ByteArray.encrypt(): ByteArray {
        val cipher: Cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        cipher.init(1, SecretKeySpec(encryptKey, "AES"), IvParameterSpec(encryptKey))
        return cipher.doFinal(this@encrypt) ?: error("Error while encrypting string.")
    }
}