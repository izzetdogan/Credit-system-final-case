package com.patika.creditsystem.model

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import org.hibernate.annotations.GenericGenerator
import java.time.LocalDate


@Entity
@Table(name="users")
data class User @JvmOverloads constructor(
    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid2")
    val id: String?=null,

    @field:NotNull(message = "National_Id cannot be empty")
    @Column(name="national_id" ,unique = true, nullable = false, length = 11)
    val nationalId: Long?,

    @field:NotNull(message = "Firstname cannot be empty")
    val firstname: String?,

    @field:NotNull(message = "Lastname cannot be empty")
    val lastname: String?,

    @field:NotNull(message = "MonthlyIncome cannot be empty")
    val monthlyIncome: Long?,

    val deposit:Long?,

    @field:NotNull(message = "PhoneNumber cannot be empty")
    val phoneNumber: String?,
    val birthdate: LocalDate?,
    var creditScore: Int?=0,

    @OneToOne(cascade = [CascadeType.REMOVE] , mappedBy = "user")
    @JsonIgnore
    var credit: Credit?=null
): BaseEntity() {
    constructor() : this(null, 0L, "", "", 0L,0L, "", LocalDate.now(),0)



}
