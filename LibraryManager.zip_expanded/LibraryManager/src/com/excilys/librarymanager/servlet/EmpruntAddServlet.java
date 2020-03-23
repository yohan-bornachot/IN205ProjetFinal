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
import com.excilys.librarymanager.modele.Membre;
import com.excilys.librarymanager.service.LivreService;
import com.excilys.librarymanager.service.LivreServiceImpl;
import com.excilys.librarymanager.service.MembreService;
import com.excilys.librarymanager.service.MembreServiceImpl;

public class EmpruntAddServlet extends HttpServlet{
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		LivreService livreServ = LivreServiceImpl.getInstance();
		MembreService membreServ = MembreServiceImpl.getInstance();
		List<Livre> livreDispoList = new ArrayList<Livre>();
		List<Membre> membreDispoList = new ArrayList<Membre>();
		try {
			livreDispoList = livreServ.getListDispo();
			membreDispoList = membreServ.getListMembreEmpruntPossible();
			request.setAttribute("livres dispo", livreDispoList);
			request.setAttribute("membres qui peuvent emprunter", membreDispoList);
			this.getServletContext().getRequestDispatcher( "/WEB-INF/Emprunt_add.jsp" ).forward( request, response );
		}
		catch(ServiceException e1) {
			throw new ServletException("Problème lors de la récupération des infos dans la base de données");
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
		if(request.getAttribute("livres dispo", )
	}
}
