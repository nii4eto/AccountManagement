package com.westernacher.service;

import java.time.LocalDate;

import org.apache.commons.lang3.StringUtils;

import com.westernacher.dto.BookDto;
import com.westernacher.entity.BookEntity;

public class BookTransformationService {

	public static BookDto transformBookEntityToBookDto(BookEntity book) {
		BookDto bookDto = new BookDto();
		
		bookDto.setId(book.getId());
		bookDto.setName(book.getName());
		bookDto.setAuthor(book.getAuthor());
		bookDto.setIsbn(book.getIsbn());
		bookDto.setYear(book.getYear().toString());
		bookDto.setQuantity(book.getQuantity());
		
		return bookDto;
	}

	public static BookEntity trasformBookDtoToBook(BookDto bookDto) {
		BookEntity book = new BookEntity();
		
		book.setId(bookDto.getId());
		book.setName(bookDto.getName());
		book.setAuthor(bookDto.getAuthor());
		book.setIsbn(bookDto.getIsbn());
		book.setYear(StringUtils.isNotBlank(bookDto.getYear()) ? LocalDate.parse(bookDto.getYear()) : null);
		book.setQuantity(bookDto.getQuantity());
		
		return book;
	}
}
