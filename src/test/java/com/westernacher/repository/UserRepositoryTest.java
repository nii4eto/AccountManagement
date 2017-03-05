package com.westernacher.repository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.westernacher.entity.UserEntity;
import com.westernacher.repository.UserRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {
	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private UserRepository userRepository;

	private static UserEntity user = new UserEntity();
	@Before
	public void test() {
		populateUser();
	}

	@Test
	public void testFindByEmail() {
		persistUserIntoEntityManager();
		Optional<UserEntity> returnedUser = userRepository.findByEmail("fakeUser@gmail.com");
		if(!returnedUser.isPresent())
			fail();

		assertEquals("fakeUser@gmail.com", returnedUser.get().getEmail());
	}

	@Test
	public void testUpdateUser() {
		persistUserIntoEntityManager();
		user.setFirstName("Ivan");
		user.setLastName("Petrov");
		userRepository.updateUser(user.getId(), user.getFirstName(), user.getLastName(), user.getDateOfBirth(), user.getPassword());
		Optional<UserEntity> returnedUser = userRepository.findByEmail("fakeUser@gmail.com");
		if(!returnedUser.isPresent())
			fail();

		assertEquals("Ivan", returnedUser.get().getFirstName());
		assertEquals("Petrov", returnedUser.get().getLastName());
	}

	private void persistUserIntoEntityManager() {
		if(entityManager.getId(user) != null)
			entityManager.merge(user);
		else
			entityManager.persist(user);
	}

	private static void populateUser() {
		user.setFirstName("Todor");
		user.setLastName("Radev");
		user.setEmail("fakeUser@gmail.com");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		user.setDateOfBirth(LocalDate.parse("2016-11-28", formatter));
	}
}