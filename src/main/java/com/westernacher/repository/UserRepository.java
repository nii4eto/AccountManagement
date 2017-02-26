package com.westernacher.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.westernacher.entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
	
	Optional<UserEntity> findByEmail(String email);

	@Modifying
	@Transactional
	@Query("UPDATE UserEntity u SET u.firstName = :firstName, u.lastName = :lastName, u.dateOfBirth = :date WHERE u.id = :id")
	void updateUser(@Param("id") Long id, @Param("firstName") String firstName,
			@Param("lastName") String lastName, @Param("date") LocalDate dateOfBirth);
}
