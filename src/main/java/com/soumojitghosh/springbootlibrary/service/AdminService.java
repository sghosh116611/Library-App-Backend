package com.soumojitghosh.springbootlibrary.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soumojitghosh.springbootlibrary.dao.BookRepository;
import com.soumojitghosh.springbootlibrary.entity.Book;
import com.soumojitghosh.springbootlibrary.requestmodels.AddBookRequest;

@Service
@Transactional
public class AdminService {

	@Autowired
	private BookRepository bookRepository;

	public void postBook(AddBookRequest addBookRequest) {
		Book book = new Book();

		book.setTitle(addBookRequest.getTitle());
		book.setAuthor(addBookRequest.getAuthor());
		book.setDescription(addBookRequest.getDescription());
		book.setCategory(addBookRequest.getCategory());
		book.setCopies(addBookRequest.getCopies());
		book.setCopiesAvailable(addBookRequest.getCopies());
		book.setImg(addBookRequest.getImage());

		bookRepository.save(book);
	}

	public void increaseBookQuantity(Long bookId) throws Exception {
		Optional<Book> book = bookRepository.findById(bookId);
		if (book == null) {
			throw new Exception("Book not found!");
		}
		book.get().setCopiesAvailable(book.get().getCopiesAvailable() + 1);
		book.get().setCopies(book.get().getCopies() + 1);

		bookRepository.save(book.get());
	}

	public void decreaseBookQuantity(Long bookId) throws Exception {
		Optional<Book> book = bookRepository.findById(bookId);
		if (book == null) {
			throw new Exception("Book not found!");
		}
		book.get().setCopiesAvailable(book.get().getCopiesAvailable() - 1);
		book.get().setCopies(book.get().getCopies() - 1);

		bookRepository.save(book.get());
	}
	
	public void deleteBook(Long bookId) throws Exception {
		Optional<Book> book = bookRepository.findById(bookId);
		if (book == null) {
			throw new Exception("Book not found!");
		}

		bookRepository.delete(book.get());
	}

}
