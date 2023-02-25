package com.patika.creditsystem.repository;

import com.patika.creditsystem.model.Credit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditRepository extends JpaRepository<Credit,String> {

}
