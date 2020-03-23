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
import com.excilys.librarymanager.service.LivreService;
import com.excilys.librarymanager.service.LivreServiceImpl;
import com.excilys.librarymanager.service.MembreService;
import com.excilys.librarymanager.service.MembreServiceImpl;

public class DashboardServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		EmpruntService empruntServ = EmpruntServiceImpl.getInstance();
		LivreService livreServ = LivreServiceImpl.getInstance();
		MembreService membreServ = MembreServiceImpl.getInstance();
		List<Emprunt> empruntList = new ArrayList<Emprunt>();

		try {
			empruntList = empruntServ.getListCurrent();
			int nb_membre = membreServ.count();
			int nb_livre = livreServ.count();
			int nb_emprunt = empruntServ.count();
			request.setAttribute("nb_membre", nb_membre);
			request.setAttribute("nb_livre", nb_livre);
			request.setAttribute("nb_emprunt", nb_emprunt);
			request.setAttribute("empruntList", empruntList);
			this.getServletContext().getRequestDispatcher( "/WEB-INF/Dashboard.jsp" ).forward( request, response );
		}
		catch(ServiceException e1) {
			System.out.println(e1.getMessage());
			e1.printStackTrace();
		}
		
	}
}
