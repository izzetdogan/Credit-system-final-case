package com.patika.creditsystem.model

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import org.hibernate.annotations.GenericGenerator

@Entity
data class Credit @JvmOverloads constructor (
    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid2")
    val id: String?=null,

    @Enumerated(EnumType.STRING)
    val creditResult: CreditResult?,

    @field:NotNull
    val creditLimit: Long?,

    @OneToOne
    @JsonIgnore
    @JoinColumn(name = "user_id")
    val user: User?=null

):BaseEntity(){
    constructor() : this(null,null,0L)
    constructor(creditResult: CreditResult? ,creditLimit: Long?, user: User?):this(null,creditResult,creditLimit,user)
}