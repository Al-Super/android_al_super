package com.centroi.alsuper.core.data.models.login

import com.google.gson.annotations.SerializedName

data class LoginRegisterRequest(
    @SerializedName("name") val name: String,
    @SerializedName("lastName") val lastName: String,
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String,
    @SerializedName("phoneNumber") val phoneNumber: String,
    @SerializedName("birthdate") val birthdate: String?
)