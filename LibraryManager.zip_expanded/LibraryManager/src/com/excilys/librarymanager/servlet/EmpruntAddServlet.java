package com.excilys.librarymanager.servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.librarymanager.exception.ServiceException;
import com.excilys.librarymanager.modele.Livre;
import com.excilys.librarymanager.modele.Membre;
import com.excilys.librarymanager.service.EmpruntService;
import com.excilys.librarymanager.service.EmpruntServiceImpl;
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
			request.setAttribute("livres_dispo", livreDispoList);
			request.setAttribute("membre_emprunteur", membreDispoList);
			this.getServletContext().getRequestDispatcher( "/WEB-INF/View/Emprunt_add.jsp" ).forward( request, response );
		}
		catch(ServiceException e1) {
			throw new ServletException("Problème lors de la récupération des infos dans la base de données");
		}
	}
	
	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            doGet(request, response);

            EmpruntService empruntService = EmpruntServiceImpl .getInstance();
            int idMembre=Integer.valueOf(request.getParameter("idMembre"));
            int idLivre=Integer.valueOf(request.getParameter("idLivre"));

            empruntService.create(idMembre, idLivre, LocalDate.now());

            this.getServletContext().getRequestDispatcher("/WEB-INF/View/emprunt_list.jsp").forward(request, response);
            
        } catch (Exception e) {
            throw new ServletException();
        }

        
	}
}
