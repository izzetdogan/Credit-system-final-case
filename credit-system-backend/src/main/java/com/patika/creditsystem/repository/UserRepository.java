package com.patika.creditsystem.repository;

import com.patika.creditsystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,String> {
    Optional<User> findUserByNationalId(Long id);
    @Query(value="select * from users u  WHERE u.national_id =?1 and u.birthdate= ?2", nativeQuery = true)
    Optional<User> findByNationalIdAndBirthDate(Long natId, LocalDate birthdate);

}
