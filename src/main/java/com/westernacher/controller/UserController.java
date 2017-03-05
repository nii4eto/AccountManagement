package com.westernacher.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.westernacher.dto.UserDto;
import com.westernacher.service.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping(value = { "/", "/index", "/users" }, method = RequestMethod.GET)
	public String goHome(Model model) {
		List<UserDto> users = userService.findAllUsers();

		model.addAttribute("users", users);
		return "users";
	}

	@RequestMapping(value = "/createUser", method = RequestMethod.GET)
	public String populateNewUser(Model model) {
		model.addAttribute("userDto", new UserDto());
		return "createUser";
	}

	@RequestMapping(value = "/createUser", method = RequestMethod.POST)
	public String createUser(@ModelAttribute UserDto userDto) {
		if (userService.findByEmail(userDto.getEmail()) != null) {
			return "error";
		}

		userService.saveUser(userDto);
		return "redirect:/users";
	}

	@RequestMapping(value = "/users/delete/{id}", method = RequestMethod.DELETE)
	public String deleteUser(@PathVariable Long id) {
		if (id == null) {
			return "error";
		}

		userService.deleteUser(id);

		return "redirect:/users";
	}

	@RequestMapping(value = "/users/add/{id}", method = RequestMethod.GET)
	public String getUserForEdit(Model model, @PathVariable Long id) {
		model.addAttribute("userDto", userService.findById(id));
		return "editUser";
	}

	@RequestMapping(value = "/editUser", method = RequestMethod.PUT)
	public String updateUser(@ModelAttribute UserDto userDto) {
		UserDto findByEmail = userService.findByEmail(userDto.getEmail());
		if (findByEmail == null || !findByEmail.equals(userDto)) {
			return "error";
		}
		
		if(StringUtils.isNotBlank(userDto.getNewPassword()) && StringUtils.isBlank(userDto.getConfirmPassword())) {
			return "error";
		}
		
		if(StringUtils.isNotBlank(userDto.getNewPassword()) && !userDto.getNewPassword().equals(userDto.getConfirmPassword())) {
			return "error";
		}

		userService.updateUser(userDto);
		return "redirect:/users";
	}
}
