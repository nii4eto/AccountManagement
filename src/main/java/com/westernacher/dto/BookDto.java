package com.westernacher.dto;

import javax.validation.constraints.Size;

import lombok.Data;

/**
 * 
 * @author Nia
 *	The purpose of this class is to
 *	create a book DTO object
 */
public @Data class BookDto {

	private Long id;

	@Size(max = 150)
	private String name;

	@Size(max = 150)
	private String author;

	private String year;

	@Size(max = 15)
	private String isbn;

	private Integer quantity;


}
