package com.centroi.alsuper.core.data.models.user

data class ForgotPasswordRequest(val email: String)
data class ForgotPasswordResponse(
    val state: String,
    val payload: ForgotPasswordPayload
)
data class ForgotPasswordPayload(
    val activationCode: ActivationCode,
    val message: String
)
data class ActivationCode(
    val userId: String,
    val expiresIn: String,
    val type: String
)

data class RestorePasswordRequest(
    val user_id: String,
    val email: String,
    val password: String,
    val confirmPassword: String,
    val code: String
)
data class RestorePasswordResponse(
    val state: String,
    val payload: RestorePasswordPayload
)
data class RestorePasswordPayload(
    val message: String
)