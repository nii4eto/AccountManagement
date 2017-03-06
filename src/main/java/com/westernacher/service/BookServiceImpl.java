package com.westernacher.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.westernacher.dto.BookDto;
import com.westernacher.entity.BookEntity;
import com.westernacher.repository.BookRepository;

@Service
@Component
@Transactional
public class BookServiceImpl implements BookService {

	private BookRepository bookRepository;

	@Autowired
	public BookServiceImpl(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}

	@Override
	public BookDto saveBook(BookDto bookDto) {
		BookEntity book = BookMapperService.mapToEntity(bookDto);
		BookEntity savedBook = bookRepository.save(book);
		

		return BookMapperService.mapToDto(savedBook);
	}

	@Override
	public BookDto findByIsbn(String isbn) {
		Optional<BookEntity> book = bookRepository.findByIsbn(isbn);
		if (!book.isPresent()) {
			return null;
		}

		BookDto bookDto = BookMapperService.mapToDto(book.get());
		return bookDto;
	}

	@Override
	public BookDto findById(Long id) {
		BookEntity book = bookRepository.findOne(id);
		BookDto bookDto = (book != null) ? BookMapperService.mapToDto(book) : new BookDto();
		return bookDto;
	}

	@Override
	public void deleteBook(Long id) {
		bookRepository.delete(id);

	}

	@Override
	public List<BookDto> findAllBooks() {
		return bookRepository.findAll().stream()
				.map(BookMapperService::mapToDto)
				.collect(Collectors.toList());
	}

	@Override
	public BookDto updateBookQuantity(BookDto bookDto) {
		BookEntity book = BookMapperService.mapToEntity(bookDto);
		bookRepository.updateBookQuantity(book.getId(), book.getQuantity());
		
		return bookDto;
	}

}
