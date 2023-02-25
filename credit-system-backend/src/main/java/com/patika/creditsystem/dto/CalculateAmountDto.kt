package com.patika.creditsystem.dto

data class CalculateAmountDto(

    val monthlyIncome: Long?,

    val deposit : Long?,

    val creditScore: Int?
)