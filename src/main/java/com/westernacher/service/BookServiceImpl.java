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
		BookEntity book = BookTransformationService.trasformBookDtoToBook(bookDto);
		bookRepository.save(book);

		return bookDto;
	}

	@Override
	public BookDto findByIsbn(String isbn) {
		Optional<BookEntity> book = bookRepository.findByIsbn(isbn);
		if (!book.isPresent()) {
			return null;
		}

		BookDto bookDto = prepareBookDto(book.get());
		return bookDto;
	}

	@Override
	public BookDto findById(Long id) {
		BookEntity book = bookRepository.findOne(id);
		BookDto bookDto = prepareBookDto(book);
		return bookDto;
	}

	@Override
	public void deleteBook(Long id) {
		bookRepository.delete(id);

	}

	@Override
	public List<BookDto> findAllBooks() {
		List<BookEntity> allBooksEntities = bookRepository.findAll();
		
		return allBooksEntities.stream()
				.map(book -> BookTransformationService.transformBookEntityToBookDto(book))
				.collect(Collectors.toList());
	}

	@Override
	public BookDto updateBookQuantity(BookDto bookDto) {
		BookEntity book = BookTransformationService.trasformBookDtoToBook(bookDto);
		bookRepository.updateBookQuantity(book.getId(), book.getQuantity());
		
		return bookDto;
	}

	private BookDto prepareBookDto(BookEntity book) {
		BookDto bookDto = (book != null) ? BookTransformationService.transformBookEntityToBookDto(book) : null;
		return bookDto;
	}
}
