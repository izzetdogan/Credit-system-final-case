package com.patika.creditsystem.dto

import com.patika.creditsystem.model.CreditResult

data class CreditAmountResultDto(
    val creditResult: CreditResult ,
    val creditLimit: Long,
    val score: Int
)