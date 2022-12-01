package com.soumojitghosh.springbootlibrary.requestmodels;

import java.util.Optional;

public class ReviewRequest {
	
	private Double rating;
	
	private Long bookId;
	
	private Optional<String> reviewDescription;
	
	ReviewRequest(){
		
	}

	public ReviewRequest(Double rating, Long bookId, Optional<String> reviewDescription) {
		this.rating = rating;
		this.bookId = bookId;
		this.reviewDescription = reviewDescription;
	}



	public Double getRating() {
		return rating;
	}

	public void setRating(Double rating) {
		this.rating = rating;
	}

	public Long getBookId() {
		return bookId;
	}

	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}

	public Optional<String> getReviewDescription() {
		return reviewDescription;
	}

	public void setReviewDescription(Optional<String> reviewDescription) {
		this.reviewDescription = reviewDescription;
	}
	

	
}
