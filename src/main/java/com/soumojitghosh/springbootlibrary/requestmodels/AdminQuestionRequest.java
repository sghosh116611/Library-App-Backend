package com.soumojitghosh.springbootlibrary.requestmodels;

public class AdminQuestionRequest {
	
	private Long id;
	
	private String response;
	
	public AdminQuestionRequest() {
		// TODO Auto-generated constructor stub
	}
	

	public AdminQuestionRequest(String response) {
		this.response = response;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}
	
	

}
