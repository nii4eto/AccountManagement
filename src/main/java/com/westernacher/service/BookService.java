package com.westernacher.service;

import java.util.List;

import com.westernacher.dto.BookDto;

public interface BookService {

	BookDto saveBook(BookDto bookDto);
	
	BookDto findByIsbn(String isbn);
	
	BookDto findById(Long id);
	
	void deleteBook(Long id);
	
	List<BookDto> findAllBooks();
	
	BookDto updateBookQuantity(BookDto bookDto);
}
