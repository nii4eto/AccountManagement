package com.westernacher.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.westernacher.dto.UserDto;
import com.westernacher.entity.UserEntity;
import com.westernacher.repository.UserRepository;

@Service
@Component
@Transactional
public class UserServiceImpl implements UserService {

	private UserRepository userRepository;

	@Autowired
	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDto saveUser(UserDto userDto) {
		UserEntity user = UserTransformationService.trasformUserDtoToUser(userDto);
		userRepository.save(user);

		return userDto;
	}

	@Override
	public UserDto findByEmail(String email) {
		Optional<UserEntity> user = userRepository.findByEmail(email);
		if (!user.isPresent()) {
			return null;
		}

		UserDto userDto = prepareUserDto(user.get());
		return userDto;
	}

	@Override
	public UserDto findById(Long id) {
		UserEntity user = userRepository.findOne(id);
		UserDto userDto = prepareUserDto(user);
		return userDto;
	}

	@Override
	public void deleteUser(Long id) {
		userRepository.delete(id);
	}

	@Override
	public UserDto updateUser(UserDto userDto) {
		UserEntity user = UserTransformationService.trasformUserDtoToUser(userDto);
		userRepository.updateUser(user.getId(), user.getFirstName(), user.getLastName(), user.getDateOfBirth());
		
		return userDto;
	}

	@Override
	public List<UserDto> findAllUsers() {
		List<UserEntity> allUsersEntities = userRepository.findAll();

		return allUsersEntities.stream().map(user -> UserTransformationService.transformUserEntityToUserDto(user))
				.collect(Collectors.toList());
	}

	private UserDto prepareUserDto(UserEntity user) {
		UserDto userDto = (user != null) ? UserTransformationService.transformUserEntityToUserDto(user) : null;
		return userDto;
	}
}
