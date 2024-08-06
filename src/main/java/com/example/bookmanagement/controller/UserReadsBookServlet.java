package com.example.bookmanagement.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.bookmanagement.dao.BookDAO;
import com.example.bookmanagement.dao.UserDAO;
import com.example.bookmanagement.dao.UserReadsBookDAO;
import com.example.bookmanagement.model.Book;
import com.example.bookmanagement.model.User;
import com.example.bookmanagement.model.UserReadsBook;
import com.google.gson.Gson;

public class UserReadsBookServlet extends HttpServlet {
	private static final Logger logger = LoggerFactory.getLogger(UserReadsBookServlet.class);
	private final BookDAO bookDAO = new BookDAO();
	private final UserDAO userDAO = new UserDAO();

	private final UserReadsBookDAO user_readsDAO = new UserReadsBookDAO();

	private final Gson gson = new Gson();

	//http://localhost:8080/book-management/api/books
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("application/json");
		logger.info("Recieved a input to AuthorServlet");
		String pathInfo = req.getPathInfo();
		if (pathInfo == null || pathInfo.equals("/")) {
			List<UserReadsBook> user_book = user_readsDAO.findAll(); 
			try (PrintWriter out = resp.getWriter()) {
				out.print(gson.toJson(user_book));
			}

		} 

		logger.info("Replied to request to get method "); 

	}
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		logger.info("Recieved a input to create  records in usersReadBook");

		resp.setContentType("application/json");

		//Input received: user_book : userId = 2, bookId = 4
		UserReadsBook user_book = gson.fromJson(req.getReader(), UserReadsBook.class);

		//find a user in the users table with Id = userid=2
		User userfoundinDatabase = userDAO.findByID((Long.parseLong(user_book.getUserId())));

		if(userfoundinDatabase == null)
		{
			logger.info("We coundlt found the user mentioned with user Id:" +user_book.getUserId() );
			return;
		}

		//find a book in the users table with Id = bookId=4
		Book bookfoundinDatabase = bookDAO.findByID((Long.parseLong(user_book.getBookId())));


		if(bookfoundinDatabase == null)
		{
			logger.info("We could not found the book mentioned with book Id:" +user_book.getBookId());
			return;
		}

		logger.info("We found both book and user with mentioned with user Id: " + user_book.getUserId() + " bookId" + user_book.getBookId());

		//user_book : userId = 2, bookId = 4, booktitle: titlevalue
		user_book.setBookTitle(bookfoundinDatabase.getTitle());

		//user_book : userId = 2, bookId = 4, booktitle: titlevalue, username : some name

		user_book.setUserName(userfoundinDatabase.getUserName());

		//saving user_book into database.
		boolean success = user_readsDAO.save(user_book);

		if (success) {
			resp.setStatus(HttpServletResponse.SC_CREATED);
		} else {
			resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
		logger.info("Succesfully created a usersReadBook record");

	}
}