package com.centroi.alsuper.core.data.models.login

import com.google.gson.annotations.SerializedName

data class LoginConfirmationRequest (
    @SerializedName("user_id") val userId: String,
    @SerializedName("code") val code: String
)