package com.westernacher.controller;

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

/**
 * 
 * @author Nia
 * The purpose of this class is to
 * manage add/remove books to logged user
 */
@Controller
public class UserBookController {

	@Autowired
	private BookService bookService;
	
	@Autowired
	private UserService userService;
	
	/**
	 * The purpose of this method is to 
	 * add new book to user or remove (return) already reserved book
	 */
	@RequestMapping(value = "/userbooks/add/{id}", method = RequestMethod.GET)
	public String addRemoveBookToUser(@PathVariable Long id) {
		BookDto bookDto = bookService.findById(id);
		
		User currentUser =  (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		UserDto userDto = userService.findByEmail(currentUser.getUsername());
		
		bookDto = userService.addRemoveBookToUser(bookDto, userDto);
		bookService.updateBookQuantity(bookDto);
		
		return "redirect:/books";
	}
	
	/**
	 * Populates all books that are added to logged user
	 *
	 */
	@RequestMapping(value = { "/userbooks/list/{id}" }, method = RequestMethod.GET)
	public String populateAllBooksForUser(Model model, @PathVariable Long id) {
		UserDto userDto = userService.findById(id);
		
		Set<BookDto> books = userDto.getBooks();

		model.addAttribute("books", books);
		return "userBooks";
	}
}
