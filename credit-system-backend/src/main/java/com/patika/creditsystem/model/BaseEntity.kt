package com.patika.creditsystem.model

import jakarta.persistence.MappedSuperclass
import java.time.LocalDateTime

@MappedSuperclass
abstract class BaseEntity(
    var updatedAt: LocalDateTime? = LocalDateTime.now()
)