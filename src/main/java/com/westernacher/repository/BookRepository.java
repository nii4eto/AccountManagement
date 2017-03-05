package com.westernacher.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.westernacher.entity.BookEntity;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Long> {
	
	Optional<BookEntity> findByIsbn(String isbn);
	
	@Modifying
	@Transactional
	@Query("UPDATE BookEntity b SET b.quantity = :quantity  WHERE b.id = :id")
	void updateBookQuantity(@Param("id") Long id, @Param("quantity") Integer quantity);
}
