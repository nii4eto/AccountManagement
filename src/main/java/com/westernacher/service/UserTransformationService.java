package com.westernacher.service;

import java.time.LocalDate;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

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
		UserEntity user = new UserEntity();
		
		user.setId(userDto.getId());
		user.setFirstName(userDto.getFirstName());
		user.setLastName(userDto.getLastName());
		user.setDateOfBirth(StringUtils.isNotBlank(userDto.getDateOfBirth()) ? LocalDate.parse(userDto.getDateOfBirth()) : null);
		user.setEmail(userDto.getEmail());
		
		user.setPassword(userDto.getPassword());
		
		if(StringUtils.isNotBlank(userDto.getNewPassword())) {
			user.setPassword(userDto.getNewPassword());
		}
		
		user.setBooks(userDto.getBooks().stream()
				.map(bookDto -> BookTransformationService.trasformBookDtoToBook(bookDto))
				.collect(Collectors.toSet()));

//		StandardPasswordEncoder encoder = new StandardPasswordEncoder("secret");
//		user.setPassword( encoder.encode(userDto.getPassword()));
		
		user.setEnabled(true);
		return user;
	}
}
