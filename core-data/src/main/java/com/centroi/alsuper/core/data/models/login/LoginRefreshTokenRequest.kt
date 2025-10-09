package com.centroi.alsuper.core.data.models.login

import com.google.gson.annotations.SerializedName

data class LoginRefreshTokenRequest(
    @SerializedName("refreshToken") val refreshToken: String
)