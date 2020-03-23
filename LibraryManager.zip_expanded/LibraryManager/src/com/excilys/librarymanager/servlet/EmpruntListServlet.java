package com.excilys.librarymanager.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.librarymanager.exception.ServiceException;
import com.excilys.librarymanager.modele.Emprunt;
import com.excilys.librarymanager.service.EmpruntService;
import com.excilys.librarymanager.service.EmpruntServiceImpl;

public class EmpruntListServlet extends HttpServlet{
	protected void doGet(HttpServletRequest request, HttpServletResponse response, boolean all) throws ServletException, IOException{
		EmpruntService empruntServ = EmpruntServiceImpl.getInstance();
		List<Emprunt> empruntList = new ArrayList<Emprunt>();
		try {
			if(!all) {
				empruntList = empruntServ.getListCurrent();
				request.setAttribute("empruntList", empruntList);
			}
			else {
				empruntList = empruntServ.getList();
				request.setAttribute("empruntList", empruntList);
			}
			this.getServletContext().getRequestDispatcher( "/WEB-INF/emprunt_list.jsp" ).forward( request, response );
		}
		catch(ServiceException e1) {
			throw new ServletException("Problème lors de la requête 'doGet' du Servlet : EmpruntListServlet");
		}
	}
}
