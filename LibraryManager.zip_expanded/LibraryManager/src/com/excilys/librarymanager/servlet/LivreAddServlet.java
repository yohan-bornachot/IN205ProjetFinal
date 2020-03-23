package com.excilys.librarymanager.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.librarymanager.exception.ServiceException;
import com.excilys.librarymanager.modele.Livre;
import com.excilys.librarymanager.modele.Membre;
import com.excilys.librarymanager.service.LivreService;
import com.excilys.librarymanager.service.LivreServiceImpl;
import com.excilys.librarymanager.service.MembreService;
import com.excilys.librarymanager.service.MembreServiceImpl;

public class LivreAddServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		LivreService livreServ = LivreServiceImpl.getInstance();
		MembreService membreServ = MembreServiceImpl.getInstance();
		List<Livre> livreDispoList = new ArrayList<Livre>();
		List<Membre> membreDispoList = new ArrayList<Membre>();
		
	}
}
