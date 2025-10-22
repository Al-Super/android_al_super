package com.centroi.alsuper.core.data.models.panic

data class PanicResponse(
    val state: String,
    val payload: SosPayload
)

data class SosPayload(
    val message: String,
    val recipients: String
)