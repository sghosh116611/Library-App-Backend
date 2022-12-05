package com.soumojitghosh.springbootlibrary.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.soumojitghosh.springbootlibrary.requestmodels.AddBookRequest;
import com.soumojitghosh.springbootlibrary.service.AdminService;
import com.soumojitghosh.springbootlibrary.utils.ExtractJWT;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/api/admin")
public class AdminController {

	@Autowired
	private AdminService adminService;
	
	@PostMapping("/secure/add/book")
	public void postBook(@RequestHeader("Authorization")String token, @RequestBody AddBookRequest addBookRequest) throws Exception {
		String admin = ExtractJWT.payloadJWTExtraction(token, "\"userType\"");
		if(admin == null || !admin.equals("Admin")) {
			throw new Exception("Only for admins");
		}
		adminService.postBook(addBookRequest);
	}
	
	@PutMapping("/secure/increase/book/quantity")
	public void increaseBookQuantity(@RequestHeader("Authorization")String token, @RequestParam Long bookId) throws Exception {
		String admin = ExtractJWT.payloadJWTExtraction(token, "\"userType\"");
		if(admin == null || !admin.equals("Admin")) {
			throw new Exception("Only for admins");
		}
		adminService.increaseBookQuantity(bookId);
	}
	
	@PutMapping("/secure/decrease/book/quantity")
	public void decreaseBookQuantity(@RequestHeader("Authorization")String token, @RequestParam Long bookId) throws Exception {
		String admin = ExtractJWT.payloadJWTExtraction(token, "\"userType\"");
		if(admin == null || !admin.equals("Admin")) {
			throw new Exception("Only for admins");
		}
		adminService.decreaseBookQuantity(bookId);
	}
	
	@DeleteMapping("/secure/delete/book")
	public void deleteBook(@RequestHeader("Authorization")String token, @RequestParam Long bookId) throws Exception {
		String admin = ExtractJWT.payloadJWTExtraction(token, "\"userType\"");
		if(admin == null || !admin.equals("Admin")) {
			throw new Exception("Only for admins");
		}
		adminService.deleteBook(bookId);
	}
}
