package com.centroi.alsuper.core.data.network

import com.centroi.alsuper.core.data.models.login.LoginRefreshTokenRequest
import com.centroi.alsuper.core.data.services.AuthUserService
import com.centroi.alsuper.core.data.userstate.UserSessionHelper
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Named

class TokenInterceptor @Inject constructor(
    private val userSessionHelper: UserSessionHelper,
    @Named("refresh") private val refreshAuthUserService: AuthUserService
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var token: String? = null
        var refreshToken: String? = null
        runBlocking {
            token = userSessionHelper.getToken()
            refreshToken = userSessionHelper.getRefreshToken()
        }
        val verifier = JWTVerifier {
            token = getNewToken(refreshToken, token)
        }

        val currentToken = token
        // if (currentToken == null || !verifier.isValid(currentToken)) {  }
        val request: Request = chain.request().newBuilder()
            .apply {
                // Use the original 'token' variable here, as it might have been refreshed
                token?.let { addHeader("Authorization", "Bearer $it") }
            }
            .build()

        return chain.proceed(request)
    }

    private fun getNewToken(refreshToken: String?, token: String?): String? {
        var token1 = token
        if (refreshToken != null) {
            runBlocking {
                val response = refreshAuthUserService.refreshTokenService(
                    LoginRefreshTokenRequest(refreshToken)
                )
                if (response.isSuccessful) {
                    val newToken = response.body()?.payload?.accessToken
                    val newRefreshToken = response.body()?.payload?.refreshToken
                    if (newToken != null && newRefreshToken != null) {
                        userSessionHelper.saveTokens(newToken, newRefreshToken)
                        token1 = newToken
                    }
                }
            }
        }
        return token1
    }
}