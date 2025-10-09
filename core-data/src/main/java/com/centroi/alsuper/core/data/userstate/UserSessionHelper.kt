package com.centroi.alsuper.core.data.userstate

import com.centroi.alsuper.core.data.extensions.EncryptionHelper
import com.centroi.alsuper.core.database.tables.UserID
import com.centroi.alsuper.core.database.tables.UserIDDao
import com.centroi.alsuper.core.database.tables.UserSession
import com.centroi.alsuper.core.database.tables.UserSessionDao
import javax.inject.Inject

class UserSessionHelper @Inject constructor(
    private val userSessionDao: UserSessionDao,
    private val userIDDao: UserIDDao
) {

    private var isAuth: Boolean = false

    suspend fun saveTokens(token: String, refreshToken: String) {
        val encryptedToken = EncryptionHelper.encrypt(token)
        val encryptedRefreshToken = EncryptionHelper.encrypt(refreshToken)
        userSessionDao.saveSession(UserSession(1, encryptedToken, encryptedRefreshToken))
    }

    suspend fun getToken(): String? =
        userSessionDao.getSession()?.encryptedToken?.let { EncryptionHelper.decrypt(it) }

    suspend fun getRefreshToken(): String? =
        userSessionDao.getSession()?.encryptedRefreshToken?.let { EncryptionHelper.decrypt(it) }

    suspend fun setUserID(id: String) {
        userIDDao.saveUserID(UserID(1, id))
    }

    suspend fun getUserId(): String {
        return userIDDao.getUserID()?.userId.orEmpty()
    }

    suspend fun isUserLoggedIn(): Boolean {
        return !getToken().isNullOrEmpty() && !getRefreshToken().isNullOrEmpty()
    }

    suspend fun closeSession() {
        clearTokens()
        isAuth = false
    }

    suspend private fun clearTokens() {
        userSessionDao.deleteSession()
    }
}