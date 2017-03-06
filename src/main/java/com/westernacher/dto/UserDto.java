package com.westernacher.dto;

import java.util.Set;

import javax.validation.constraints.Size;

import lombok.Data;

/**
 * 
 * @author Nia
 *	The purpose of this class is to
 *	create a user DTO object
 */
public @Data class UserDto {
	private Long id;

	@Size(max = 45)
	private String firstName;

	@Size(max = 45)
	private String lastName;

	@Size(min = 5, max = 100)
	private String email;

	private String dateOfBirth;

	@Size(min = 5, max = 255)
	private String password;

	@Size(min = 5, max = 255)
	private String newPassword;

	@Size(min = 5, max = 255)
	private String confirmPassword;
	
	private Set<BookDto> books;
}
