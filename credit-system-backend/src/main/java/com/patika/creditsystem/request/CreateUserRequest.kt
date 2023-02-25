package com.patika.creditsystem.request

import com.fasterxml.jackson.annotation.JsonFormat
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class CreateUserRequest(
    @field:NotNull(message = "National Id cannot be empty")
    val nationalId: Long?,
    @field:NotBlank(message="firstname cannot be empty")
    val firstname: String?,
    @field:NotBlank(message = "lastname cannot be empty")
    val lastname: String?,
    @field:NotNull(message = "Monthly Income ot be empty")
    val monthlyIncome: Long?,
    val deposit: Long?=0L,
    @field:NotBlank(message = "Phone Number cannot be empty")
    val phoneNumber: String?,
    @field:NotBlank(message = "birthdate cannot be empty | Ex 2015-01-01")
    @JsonFormat(pattern="yyyy-MM-dd")
    val birthdate: String?,
)
