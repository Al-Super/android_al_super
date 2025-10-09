package com.centroi.alsuper.core.data.models.contacts

data class ContactResponse(
    val state: String,
    val payload: ContactPayload,
    val message: String?
)