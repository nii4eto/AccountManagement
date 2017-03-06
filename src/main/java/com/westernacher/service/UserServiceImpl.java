package com.westernacher.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.westernacher.dto.BookDto;
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
		UserEntity user = UserMapperService.mapToEntity(userDto);
		UserEntity savedUser = userRepository.save(user);
		
		return UserMapperService.mapToDto(savedUser);
	}

	@Override
	public UserDto findByEmail(String email) {
		Optional<UserEntity> user = userRepository.findByEmail(email);
		if (!user.isPresent()) {
			return null;
		}

		UserDto userDto = UserMapperService.mapToDto(user.get());
		return userDto;
	}

	@Override
	public UserDto findById(Long id) {
		UserEntity user = userRepository.findOne(id);
		UserDto userDto = (user != null) ? UserMapperService.mapToDto(user) : new UserDto();
		return userDto;
	}

	@Override
	public void deleteUser(Long id) {
		userRepository.delete(id);
	}

	@Override
	public UserDto updateUser(UserDto userDto) {
		UserEntity user = UserMapperService.mapToEntity(userDto);
		userRepository.updateUser(user.getId(), user.getFirstName(), user.getLastName(), user.getDateOfBirth(), user.getPassword());
		
		return userDto;
	}

	@Override
	public List<UserDto> findAllUsers() {

		return userRepository.findAll().stream()
				.map(UserMapperService::mapToDto)
				.collect(Collectors.toList());
	}
	
	@Override
	public BookDto addRemoveBookToUser(BookDto bookDto, UserDto userDto) {
		Set<BookDto> books = userDto.getBooks() == null ? new HashSet<>() : userDto.getBooks();
		Integer currentQuantity = bookDto.getQuantity();
		
		if(books.contains(bookDto)) {
			books.remove(bookDto);
			bookDto.setQuantity(++currentQuantity);
		} else {
			books.add(bookDto);
			bookDto.setQuantity(--currentQuantity);
		}
		
		userDto.setBooks(books);
		saveUser(userDto);
		
		return bookDto;
	}
}
