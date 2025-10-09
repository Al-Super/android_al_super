package com.centroi.alsuper.core.data.models.login

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("state")
    val state: String,

    @SerializedName("payload")
    val payload: LoginPayload
)

data class LoginPayload(
    @SerializedName("user")
    val user: User,

    @SerializedName("token")
    val token: String,

    @SerializedName("refreshToken")
    val refreshToken: String
)

data class User(
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
    val updatedAt: String
)