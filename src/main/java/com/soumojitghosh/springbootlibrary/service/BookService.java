package com.soumojitghosh.springbootlibrary.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soumojitghosh.springbootlibrary.dao.BookRepository;
import com.soumojitghosh.springbootlibrary.dao.CheckoutRepository;
import com.soumojitghosh.springbootlibrary.dao.HistoryRepository;
import com.soumojitghosh.springbootlibrary.entity.Book;
import com.soumojitghosh.springbootlibrary.entity.Checkout;
import com.soumojitghosh.springbootlibrary.entity.History;
import com.soumojitghosh.springbootlibrary.responsemodels.ShelfCurrentLoansResponse;

@Service
@Transactional
public class BookService {

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private CheckoutRepository checkoutRepository;

	@Autowired
	private HistoryRepository historyRepository;

	public Book checkoutBook(String userEmail, Long bookId) throws Exception {

		Optional<Book> book = bookRepository.findById(bookId);

		Checkout validateCheckout = checkoutRepository.findByUserEmailAndBookId(userEmail, bookId);

		if (!book.isPresent() || validateCheckout != null || book.get().getCopiesAvailable() <= 0) {
			throw new Exception("Book doesnot exist or already checked out by user");
		}

		book.get().setCopiesAvailable(book.get().getCopiesAvailable() - 1);
		bookRepository.save(book.get());

		Checkout checkout = new Checkout(userEmail, LocalDate.now().toString(), LocalDate.now().plusDays(7).toString(),
				book.get().getId());

		checkoutRepository.save(checkout);

		return book.get();
	}

	public boolean checkedoutBookByUser(String userEmail, Long bookId) throws Exception {
		Checkout validateCheckout = checkoutRepository.findByUserEmailAndBookId(userEmail, bookId);
		if (validateCheckout != null) {
			return true;
		} else {
			return false;
		}
	}

	public int currentLoansCount(String userEmail) throws Exception {
		return checkoutRepository.findBooksByUserEmail(userEmail).size();
	}

	public List<ShelfCurrentLoansResponse> currentLoans(String userEmail) {

		List<ShelfCurrentLoansResponse> shelfCurrentLoansResponses = new ArrayList<>();

		List<Checkout> checkOutList = checkoutRepository.findBooksByUserEmail(userEmail);
		List<Long> bookIdList = new ArrayList<>();

		for (Checkout checkout : checkOutList) {
			bookIdList.add(checkout.getBookId());
		}

		List<Book> books = bookRepository.findBooksByBookIds(bookIdList);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		for (Book book : books) {
			Optional<Checkout> checkout = checkOutList.stream().filter(x -> x.getBookId() == book.getId()).findFirst();

			if (checkout.isPresent()) {

				Date d1 = null;
				Date d2 = null;
				try {
					d1 = sdf.parse(checkout.get().getReturnDate());
					d2 = sdf.parse(LocalDate.now().toString());
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				TimeUnit time = TimeUnit.DAYS;

				long timeDifference = time.convert(d1.getTime() - d2.getTime(), TimeUnit.MILLISECONDS);

				shelfCurrentLoansResponses.add(new ShelfCurrentLoansResponse(book, (int) timeDifference));
			}
		}
		return shelfCurrentLoansResponses;
	}

	public void returnBook(String userEmail, Long bookId) throws Exception {
		Optional<Book> book = bookRepository.findById(bookId);

		Checkout validateCheckout = checkoutRepository.findByUserEmailAndBookId(userEmail, bookId);

		if (!book.isPresent() || validateCheckout == null) {
			throw new Exception("Book doesnot exist or not checked out");
		}

		book.get().setCopiesAvailable(book.get().getCopiesAvailable() + 1);

		bookRepository.save(book.get());
		checkoutRepository.deleteById(validateCheckout.getId());

		// Saving the history for the user
		History history = new History(userEmail, validateCheckout.getReturnDate(), LocalDate.now().toString(),
				book.get().getTitle(), book.get().getAuthor(), book.get().getDescription(), book.get().getImg());
		
		historyRepository.save(history);
	}

	public void renewLoan(String userEmail, Long bookId) throws Exception {
		Checkout validateCheckout = checkoutRepository.findByUserEmailAndBookId(userEmail, bookId);

		if (validateCheckout == null) {
			throw new Exception("Book doesnot exist or not checked out by user");
		}

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date d1 = sdf.parse(validateCheckout.getReturnDate());
		Date d2 = sdf.parse(LocalDate.now().toString());

		if (d1.compareTo(d2) > 0 || d1.compareTo(d2) == 0) {
			validateCheckout.setReturnDate(LocalDate.now().plusDays(7).toString());
			checkoutRepository.save(validateCheckout);
		}
	}

}
