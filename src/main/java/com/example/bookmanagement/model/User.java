package com.example.bookmanagement.model;

public class User {
	private long  user_id;
	  private String user_name;
	  private String user_phonenumber;
	  private String user_email;
	 
	  public long getUserId() {
		  return user_id;
	  }
	  public void setUserId(long user_id) {
		  this.user_id=user_id;
	  }
	  public String getUserName() {
		  return user_name;
	  }
	  public void setUserName(String user_name) {
		  this.user_name=user_name;
	  }
	  public String getUserPhonenumber() {
		  return user_phonenumber;
	  }
	  public void setUserPhonenumber(String user_phonenumber) {
		  this.user_phonenumber=user_phonenumber;
	  }
	  public String getUserEmail() {
		  return user_email;
	  }
	  public void setUserEmail(String user_email) {
		  this.user_email=user_email;
	  }

}
