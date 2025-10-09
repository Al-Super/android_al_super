package com.centroi.alsuper.core.data.network

import android.util.Base64
import org.json.JSONObject
import javax.inject.Inject

class JWTVerifier @Inject constructor (
    private val onExpired: () -> Unit
) {
    fun isValid(jwt: String): Boolean {
        val parts = jwt.split(".")
        if (parts.size != 3) return false
        val payload = String(Base64.decode(parts[1], Base64.URL_SAFE or Base64.NO_PADDING or Base64.NO_WRAP))
        val exp = JSONObject(payload).optLong("exp", 0)
        val now = System.currentTimeMillis() / 1000
        return if (exp > now) true else { onExpired(); false }
    }
}