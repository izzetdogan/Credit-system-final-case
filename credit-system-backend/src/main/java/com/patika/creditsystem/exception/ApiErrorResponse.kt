package com.patika.creditsystem.exception

data class ApiErrorResponse(
    val message: String,
    val success: Boolean,
)