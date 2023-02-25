package com.patika.creditsystem.request

import jakarta.validation.constraints.NotBlank

data class CreateCreditDto(
    @field:NotBlank(message = "National Id cannot be empty")
    val userId: String?,
)