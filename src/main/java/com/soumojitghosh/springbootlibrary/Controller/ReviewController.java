package com.soumojitghosh.springbootlibrary.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.soumojitghosh.springbootlibrary.requestmodels.ReviewRequest;
import com.soumojitghosh.springbootlibrary.service.ReviewService;
import com.soumojitghosh.springbootlibrary.utils.ExtractJWT;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

	@Autowired
	private ReviewService reviewService;
	
	@GetMapping("/secure/user/book")
	public Boolean postReview(@RequestHeader(value = "Authorization") String token, 
			@RequestParam Long bookId) throws Exception {
		String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
		if(userEmail == null) {
			throw new Exception("User email is missing");
		}
		
		return reviewService.userReviewListed(userEmail, bookId);
	}

	@PostMapping("/secure")
	public void postReview(@RequestHeader(value = "Authorization") String token, 
			@RequestBody ReviewRequest reviewRequest) throws Exception {
		String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
		if(userEmail == null) {
			throw new Exception("User email is missing");
		}
		
		reviewService.postReview(userEmail, reviewRequest);
	}

}
