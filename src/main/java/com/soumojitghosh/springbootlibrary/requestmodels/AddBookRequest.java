package com.soumojitghosh.springbootlibrary.requestmodels;

public class AddBookRequest {

	private String title;

	private String author;

	private String description;

	private int copies;

	private String category;

	private String image;

	AddBookRequest() {

	}

	public AddBookRequest(String title, String author, String description, int copies, String category, String image) {
		this.title = title;
		this.author = author;
		this.description = description;
		this.copies = copies;
		this.category = category;
		this.image = image;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getCopies() {
		return copies;
	}

	public void setCopies(int copies) {
		this.copies = copies;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

}
