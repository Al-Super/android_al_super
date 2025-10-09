package com.centroi.alsuper.core.data.models.contacts

data class RemoteContact(
    val id: Int,
    val name: String,
    val lastName: String?,
    val phoneNumber: String,
    val isMain: Boolean,
    val userId: String?,
    val createdAt: String?,
    val updatedAt: String?
)