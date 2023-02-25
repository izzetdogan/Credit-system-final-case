package com.patika.creditsystem.dto

import com.patika.creditsystem.model.CreditResult

data class SendSmsDto(
    val message: String?,
    val nationalId: Long?,
    val phoneNumber: String?,
    val creditLimit: Long?,
    val creditResult: CreditResult?,
)
