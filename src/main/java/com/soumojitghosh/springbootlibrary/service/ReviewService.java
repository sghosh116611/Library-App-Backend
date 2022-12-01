package com.soumojitghosh.springbootlibrary.service;

import java.sql.Date;
import java.time.LocalDate;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soumojitghosh.springbootlibrary.dao.BookRepository;
import com.soumojitghosh.springbootlibrary.dao.ReviewRepository;
import com.soumojitghosh.springbootlibrary.entity.Review;
import com.soumojitghosh.springbootlibrary.requestmodels.ReviewRequest;

@Service
@Transactional
public class ReviewService {

	@Autowired
	private ReviewRepository reviewRepository;

	public void postReview(String userEmail, ReviewRequest reviewRequest) throws Exception {
		Review validateReview = reviewRepository.findByUserEmailAndBookId(userEmail, reviewRequest.getBookId());

		if (validateReview != null) {
			throw new Exception("Review alreay created");
		}

		Review review = new Review();

		review.setBookId(reviewRequest.getBookId());
		review.setRating(reviewRequest.getRating());
		review.setUserEmail(userEmail);
		if (reviewRequest.getReviewDescription().isPresent()) {
			review.setReviewDescription(reviewRequest.getReviewDescription()
										.map(Object::toString).orElse(null));
			;
		}
		review.setDate(Date.valueOf(LocalDate.now()));
		reviewRepository.save(review);

	}

	public boolean userReviewListed(String userEmail, Long bookId) {
		Review reviewValidate = reviewRepository.findByUserEmailAndBookId(userEmail, bookId);
		if (reviewValidate != null) {
			return true;
		}
		return false;
	}

}
