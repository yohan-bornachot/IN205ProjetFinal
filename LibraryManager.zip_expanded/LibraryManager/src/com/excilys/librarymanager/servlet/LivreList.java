package com.excilys.librarymanager.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.librarymanager.exception.ServiceException;
import com.excilys.librarymanager.modele.Livre;
import com.excilys.librarymanager.service.LivreService;
import com.excilys.librarymanager.service.LivreServiceImpl;


public class LivreList extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LivreService livreServ = LivreServiceImpl.getInstance();
		List<Livre> livreList = new ArrayList<Livre>();
		try {
			livreList = livreServ.getListDispo();
			request.setAttribute("livreList", livreList);
			this.getServletContext().getRequestDispatcher( "/WEB-INF/Dashboard.jsp" ).forward( request, response );
		}
		catch(ServiceException e1) {
			System.out.println(e1.getMessage());
			e1.printStackTrace();
		}	
	}
}

