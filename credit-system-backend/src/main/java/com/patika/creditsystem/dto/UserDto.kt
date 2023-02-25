package com.patika.creditsystem.dto

import com.fasterxml.jackson.annotation.JsonFormat
import com.patika.creditsystem.model.Credit
import com.patika.creditsystem.model.User
import java.time.LocalDate
import java.time.LocalDateTime

data class UserDto constructor(
    val id: String?,
    val nationalId: Long?,
    val firstname: String?,
    val lastname: String?,
    val monthlyIncome: Long?,
    val deposit: Long?=0,
    val phoneNumber: String?,
    @JsonFormat(pattern="yyyy-MM-dd")
    val birthdate: LocalDate?,
    val creditScore: Int?,
    var credit: Credit?,
    val updatedAt: LocalDateTime?
) {

    companion object
    {
        @JvmStatic
        fun convert(from: User): UserDto{
            return UserDto(
                from.id,
                from.nationalId,
                from.firstname,
                from.lastname,
                from.monthlyIncome,
                from.deposit,
                from.phoneNumber,
                from.birthdate,
                from.creditScore,
                from.credit,
                from.updatedAt
            )
        }

    }

}