package com.centroi.alsuper.core.data.models.login

import com.google.gson.annotations.SerializedName


data class LoginRegisterResponse(
    @SerializedName("state")
    val state: String,

    @SerializedName("payload")
    val payload: Payload
)

data class Payload(
    @SerializedName("user")
    val user: RegisterUser,

    @SerializedName("activationCode")
    val activationCode: ActivationCode
)
data class RegisterUser(
    @SerializedName("id")
    val id: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("lastName")
    val lastName: String?,

    @SerializedName("email")
    val email: String,

    @SerializedName("phoneNumber")
    val phoneNumber: String,

    @SerializedName("birthdate")
    val birthdate: String?,

    @SerializedName("isActive")
    val isActive: Boolean,

    @SerializedName("accountVerifiedAt")
    val accountVerifiedAt: String?,

    @SerializedName("createdAt")
    val createdAt: String,

    @SerializedName("updatedAt")
    val updatedAt: String,

    @SerializedName("deletedAt")
    val deletedAt: String?
)

data class ActivationCode(
    @SerializedName("expiresIn")
    val expiresIn: String
)