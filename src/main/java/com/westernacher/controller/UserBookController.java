package com.westernacher.controller;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.westernacher.dto.BookDto;
import com.westernacher.dto.UserDto;
import com.westernacher.service.BookService;
import com.westernacher.service.UserService;

@Controller
public class UserBookController {

	@Autowired
	private BookService bookService;
	
	@Autowired
	private UserService userService;
	
	
	@RequestMapping(value = "/userbooks/add/{id}", method = RequestMethod.GET)
	public String getBookForUser(@PathVariable Long id) {
		BookDto bookDto = bookService.findById(id);
		Integer currentQuantity = bookDto.getQuantity();
		
		User currentUser =  (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		UserDto userDto = userService.findByEmail(currentUser.getUsername());
		
		Set<BookDto> books = userDto.getBooks() == null ? new HashSet<>() : userDto.getBooks();
		
		if(books.contains(bookDto)) {
			books.remove(bookDto);
			bookDto.setQuantity(++currentQuantity);
		} else {
			books.add(bookDto);
			bookDto.setQuantity(--currentQuantity);
		}
		
		userDto.setBooks(books);
		
		userService.saveUser(userDto);
		bookService.updateBookQuantity(bookDto);
		
		return "redirect:/books";
	}
	
	@RequestMapping(value = { "/userbooks/list/{id}" }, method = RequestMethod.GET)
	public String goHome(Model model, @PathVariable Long id) {
		UserDto userDto = userService.findById(id);
		
		Set<BookDto> books = userDto.getBooks();

		model.addAttribute("books", books);
		return "userBooks";
	}
}
