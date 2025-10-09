package com.centroi.alsuper.core.data.models.contacts

data class ContactRequest(
    val name: String,
    val lastName: String,
    val phoneNumber: String,
    val isMain: Boolean
)
