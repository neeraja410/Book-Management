package com.example.bookmanagement.model;

public class Book {
  private long  id;
  private String title;
  private String author;
  private String isbn;
  public long getID() {
	  return id;
  }
  public void setID(long id) {
	  this.id=id;
  }
  public String getTitle() {
	  return title;
  }
  public void setTitle(String title) {
	  this.title=title;
  }
  public String getAuthor() {
	  return author;
  }
  public void setAuthor(String author) {
	  this.author=author;
  }
  public String getIsbn() {
	  return isbn;
  }
  public void setIsbn(String isbn) {
	  this.isbn=isbn;
  }
}
