package com.example.bookmanagement.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.bookmanagement.dao.UserDAO;
import com.example.bookmanagement.dao.UserReadsBookDAO;
import com.example.bookmanagement.model.User;
import com.example.bookmanagement.model.UserReadsBook;
import com.google.gson.Gson;

public class FindUserPhonenumberServlet extends HttpServlet{
	private static final Logger logger = LoggerFactory.getLogger(FindUserPhonenumberServlet.class);
	private final Gson gson = new Gson();
	UserReadsBookDAO userReadBooksDao=new UserReadsBookDAO();
	UserDAO userDao=new UserDAO();

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.info("Recieved a input to get  records from usersReadBook");
		String book_id=request.getParameter("book_id");
		//request.setAttribute("book_id",book_id);

		UserReadsBook readsBook =  userReadBooksDao.findByID(book_id);
		if(readsBook!=null && readsBook.getUserId()!=null)
		{
			logger.info("readsBook for book id is not null and user is also not null: UserId " + readsBook.getUserId() );
		}
		else {
			logger.info("Recieved null -- user not found in UserReadsBook" );
		}

		User userfoundinDatabase =userDao.findByID(Long.parseLong(readsBook.getUserId()));

		if(userfoundinDatabase!=null)
		{
			logger.info("userfoundinDatabase is NOT null" );
		}
		else {
			logger.info("userfoundinDatabase is NULL. We should stop..." );
		}
		String user_required_phonenumber=userfoundinDatabase.getUserPhonenumber();

		logger.info("Recieved a input to get the phone number from user ");
		if(user_required_phonenumber==null) {
			logger.info(" phone number not found in user table with input book id");
		}
		response.setContentType("application/json");
		if (user_required_phonenumber != null) {
			try (PrintWriter out = response.getWriter()) {
				out.print(gson.toJson(user_required_phonenumber));
			}
		}
	}
}
