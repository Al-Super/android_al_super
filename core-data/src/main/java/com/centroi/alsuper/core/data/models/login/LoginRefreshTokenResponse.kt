package com.centroi.alsuper.core.data.models.login

import com.google.gson.annotations.SerializedName

data class LoginRefreshTokenResponse(
    @SerializedName("state")
    val state: String,

    @SerializedName("payload")
    val payload: RefreshTokenPayload
)

data class RefreshTokenPayload(
    @SerializedName("accessToken")
    val accessToken: String,

    @SerializedName("refreshToken")
    val refreshToken: String
)