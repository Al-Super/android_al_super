package com.centroi.alsuper.core.data.models.user

data class AuthMeResponse(
    val state: String,
    val payload: AuthMePayload
)

data class AuthMePayload(
    val user: AuthUser
)

data class AuthUser(
    val id: String,
    val name: String,
    val lastName: String?,
    val email: String,
    val phoneNumber: String,
    val birthdate: String?,
    val isActive: Boolean,
    val accountVerifiedAt: String?,
    val createdAt: String?,
    val updatedAt: String?
)