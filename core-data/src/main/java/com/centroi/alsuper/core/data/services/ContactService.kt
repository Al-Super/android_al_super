package com.centroi.alsuper.core.data.services

import com.centroi.alsuper.core.data.models.contacts.ContactRequest
import com.centroi.alsuper.core.data.models.contacts.ContactResponse
import com.centroi.alsuper.core.data.models.contacts.ContactsListResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ContactService {
    @POST("/api/contacts")
    suspend fun addContact(@Body request: ContactRequest): Response<ContactResponse>

    @GET("/api/contacts")
    suspend fun getContacts(): Response<ContactsListResponse>

    @GET("/api/contacts/{id}")
    suspend fun getContact(@Path("id") id: Int): Response<ContactResponse>

    @PUT("/api/contacts/{id}")
    suspend fun updateContact(@Path("id") id: Int, @Body request: ContactRequest): Response<ContactResponse>

    @DELETE("/api/contacts/{id}")
    suspend fun deleteContact(@Path("id") id: Int): Response<ContactResponse>
}