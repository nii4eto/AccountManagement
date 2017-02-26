package com.westernacher.service;

import java.time.LocalDate;

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

		return userDTO;
	}

	public static UserEntity trasformUserDtoToUser(UserDto userDto) {
		UserEntity user = new UserEntity();
		
		user.setId(userDto.getId());
		user.setFirstName(userDto.getFirstName());
		user.setLastName(userDto.getLastName());
		user.setDateOfBirth(StringUtils.isNotBlank(userDto.getDateOfBirth()) ? LocalDate.parse(userDto.getDateOfBirth()) : null);
		user.setEmail(userDto.getEmail());

		return user;
	}
}
