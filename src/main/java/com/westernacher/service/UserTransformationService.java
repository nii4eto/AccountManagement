package com.westernacher.service;

import java.time.LocalDate;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.westernacher.dto.UserDto;
import com.westernacher.entity.UserEntity;

public class UserTransformationService {
	
	public static UserDto transformUserEntityToUserDto(UserEntity user) {
		UserDto userDTO = new UserDto();
		
		userDTO.setId(user.getId());
		userDTO.setFirstName(user.getFirstName());
		userDTO.setLastName(user.getLastName());
		userDTO.setDateOfBirth(user.getDateOfBirth().toString());
		userDTO.setEmail(user.getEmail());
		userDTO.setPassword(user.getPassword());
		
		userDTO.setBooks(user.getBooks().stream()
				.map(book -> BookTransformationService.transformBookEntityToBookDto(book))
				.collect(Collectors.toSet()));

		return userDTO;
	}

	public static UserEntity trasformUserDtoToUser(UserDto userDto) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		UserEntity user = new UserEntity();
		
		user.setId(userDto.getId());
		user.setFirstName(userDto.getFirstName());
		user.setLastName(userDto.getLastName());
		user.setDateOfBirth(StringUtils.isNotBlank(userDto.getDateOfBirth()) ? LocalDate.parse(userDto.getDateOfBirth()) : null);
		user.setEmail(userDto.getEmail());
		
		if(StringUtils.isNotBlank(userDto.getPassword())) {
			user.setPassword(encoder.encode(userDto.getPassword()));
		}
		
		if(StringUtils.isNotBlank(userDto.getNewPassword())) {
			user.setPassword(encoder.encode(userDto.getNewPassword()));
		}
		
		if(userDto.getBooks() != null) {
			user.setBooks(userDto.getBooks().stream()
					.map(bookDto -> BookTransformationService.trasformBookDtoToBook(bookDto))
					.collect(Collectors.toSet()));
		}
		
		user.setEnabled(true);
		return user;
	}
}
