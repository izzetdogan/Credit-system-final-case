package com.patika.creditsystem.dto

import com.patika.creditsystem.model.Credit
import com.patika.creditsystem.model.CreditResult


data class CreditDto (
    val id: String?,
    val creditResult: CreditResult?,
    val creditLimit: Long?,
    val userDto: UserDto?,

){
    companion object{
        @JvmStatic
        fun convert(from:Credit) :CreditDto{
            return CreditDto(
                from.id,
                from.creditResult,
                from.creditLimit,
                from.user?.let { UserDto.convert(it) },

            )
        }
        @JvmStatic
        fun convertToCredit(from:CreditDto) :Credit{
            return Credit(
                from.id,
                from.creditResult,
                from.creditLimit,

            )
        }
    }
}