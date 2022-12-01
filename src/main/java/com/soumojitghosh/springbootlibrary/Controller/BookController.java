package com.soumojitghosh.springbootlibrary.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.soumojitghosh.springbootlibrary.entity.Book;
import com.soumojitghosh.springbootlibrary.responsemodels.ShelfCurrentLoansResponse;
import com.soumojitghosh.springbootlibrary.service.BookService;
import com.soumojitghosh.springbootlibrary.utils.ExtractJWT;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/api/books")
public class BookController {

	@Autowired
	private BookService bookService;

	@PutMapping("/secure/checkout")
	public Book checkoutBook(@RequestHeader(value = "Authorization") String token, @RequestParam("bookId") Long bookId)
			throws Exception {
		String userEmail = ExtractJWT.payloadJWTExtraction(token,"\"sub\"");
		return bookService.checkoutBook(userEmail, bookId);
	}

	@GetMapping("/secure/ischeckedout/byuser")
	public Boolean checkedoutBookByUser(@RequestHeader(value = "Authorization") String token,
			@RequestParam("bookId") Long bookId) throws Exception {
		String userEmail = ExtractJWT.payloadJWTExtraction(token,"\"sub\"");
		return bookService.checkedoutBookByUser(userEmail, bookId);
	}

	@GetMapping("/secure/currentloan/count")
	public int currentLoansCount(@RequestHeader(value = "Authorization") String token) throws Exception {
		String userEmail = ExtractJWT.payloadJWTExtraction(token,"\"sub\"");
		return bookService.currentLoansCount(userEmail);
	}
	
	@GetMapping("/secure/currentloans")
	public List<ShelfCurrentLoansResponse> currentLoans(@RequestHeader("Authorization") String token) throws Exception{
		String userEmail = ExtractJWT.payloadJWTExtraction(token,"\"sub\"");
		return bookService.currentLoans(userEmail);
	}
	
	@PutMapping("/secure/return")
	public void returnBook(@RequestHeader("Authorization") String token, @RequestParam("bookId") Long bookId) throws Exception{
		String userEmail = ExtractJWT.payloadJWTExtraction(token,"\"sub\"");
		bookService.returnBook(userEmail,bookId);
	}
	
	@PutMapping("/secure/renew")
	public void renewLoan(@RequestHeader("Authorization") String token, @RequestParam("bookId") Long bookId) throws Exception{
		String userEmail = ExtractJWT.payloadJWTExtraction(token,"\"sub\"");
		bookService.renewLoan(userEmail,bookId);
	}
}
