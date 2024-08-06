package com.example.bookmanagement.model;

public class UserReadsBook {

		private long  usersReadBook_id;
		  private String user_id;
		  private String user_name;
		  private String book_id;
		  private String book_title;
		  public long getusersReadBook_id() {
			  return usersReadBook_id;
		  }
		  public void setusersReadBook_id(long usersReadBook_id) {
			  this.usersReadBook_id=usersReadBook_id;
		  }
		  public String getUserId() {
			  return user_id;
		  }
		  public void setUserId(String user_id) {
			  this.user_id=user_id;
		  }
		  public String getUserName() {
			  return user_name;
		  }
		  public void setUserName(String user_name) {
			  this.user_name=user_name;
		  }
		  public String getBookId() {
			  return book_id;
		  }
		  public void setBookId(String book_id) {
			  this.book_id=book_id;
		  }
		  public String getBookTitle() {
			  return book_title;
		  }
		  public void setBookTitle(String book_title) {
			  this.book_title=book_title;
		  }

	}



