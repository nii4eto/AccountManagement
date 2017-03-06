package com.westernacher.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.westernacher.dto.UserDto;
import com.westernacher.entity.UserEntity;
import com.westernacher.repository.UserRepository;

@RunWith(SpringJUnit4ClassRunner.class)
public class UserServiceTest {

	private static final String EMAIL = "test@gmail.com";

	@Mock
	private UserServiceImpl userServiceImpl;

	@Mock
	private UserRepository userRepository;

	@Before
	public void setup() {
		userServiceImpl = new UserServiceImpl(userRepository);
	}

	@Test
	public void testSaveUserSuccess() {
		UserEntity user = new UserEntity(EMAIL);
		when(this.userRepository.save(any(UserEntity.class))).thenReturn(user);
		
		UserDto userDto = prepareUser(EMAIL);
		UserDto savedUser = userServiceImpl.saveUser(userDto);

		assertNotNull(savedUser);
		assertEquals(EMAIL, savedUser.getEmail());
	}
	
	@Test
	public void testFindByEmail() {
		UserEntity user = UserMapperService.mapToEntity(prepareUser(EMAIL));
		
		when(this.userRepository.findByEmail(EMAIL)).thenReturn(Optional.of(user));
		
		UserDto userDto = prepareUser(EMAIL);
		UserDto findByEmail = userServiceImpl.findByEmail(userDto.getEmail());
		
		assertEquals(EMAIL, findByEmail.getEmail());
	}

	private UserDto prepareUser(String email) {
		UserDto userDto = new UserDto();
		userDto.setFirstName("Nia");
		userDto.setLastName("Doncheva");
		userDto.setDateOfBirth("2017-01-01");
		userDto.setEmail(email);

		return userDto;
	}
}
