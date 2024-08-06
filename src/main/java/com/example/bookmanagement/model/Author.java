package com.example.bookmanagement.model;

public class Author {
	private long  author_id;
	  private String author_name;
	  private String author_phonenumber;
	  public long getauthorId() {
		  return author_id;
	  }
	  public void setID(long author_id) {
		  this.author_id=author_id;
	  }
	  public String getAuthorName() {
		  return author_name;
	  }
	  public void setTitle(String author_name) {
		  this.author_name=author_name;
	  }
	  public String getAuthorPhonenumber() {
		  return author_phonenumber;
	  }
	  public void setAuthor(String author_phonenumber) {
		  this.author_phonenumber=author_phonenumber;
	  }

}
