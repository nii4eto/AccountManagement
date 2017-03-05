package com.westernacher.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.westernacher.dto.BookDto;
import com.westernacher.service.BookService;

@Controller
public class BookController {

	@Autowired
	private BookService bookService;
	
	@RequestMapping(value = { "/books" }, method = RequestMethod.GET)
	public String findAllBooks(Model model) {
		List<BookDto> books = bookService.findAllBooks();

		model.addAttribute("books", books);
		return "books";
	}
	
	@RequestMapping(value = "/addBook", method = RequestMethod.GET)
	public String populateNewBook(Model model) {
		model.addAttribute("bookDto", new BookDto());
		return "addBook";
	}

	@RequestMapping(value = "/addBook", method = RequestMethod.POST)
	public String createUser(@ModelAttribute BookDto bookDto) {
		if (bookService.findByIsbn(bookDto.getIsbn()) != null) {
			return "error";
		}

		bookService.saveBook(bookDto);
		return "redirect:/books";
	}
	
	@RequestMapping(value = "/books/delete/{id}", method = RequestMethod.DELETE)
	public String deleteUser(@PathVariable Long id) {
		if (id == null) {
			return "error";
		}

		bookService.deleteBook(id);

		return "redirect:/books";
	}
}
