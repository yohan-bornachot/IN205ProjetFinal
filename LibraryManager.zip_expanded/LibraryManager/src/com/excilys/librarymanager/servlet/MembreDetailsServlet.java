package com.excilys.librarymanager.servlet;

import javax.servlet.http.*;
import javax.servlet.*;
import java.io.IOException;
import java.util.List;

import com.excilys.librarymanager.modele.*;
import com.excilys.librarymanager.service.MembreService;
import com.excilys.librarymanager.service.MembreServiceImpl;

public class MembreDetailsServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException , IOException {
        try {

            int id=Integer.valueOf(request.getParameter("id"));
            MembreServiceImpl membreService = MembreServiceImpl.getInstance();
            Membre membre=membreService.getById(id);
            EmpruntService empruntService = EmpruntServiceImpl.getInstance();
            List<Emprunt> listEmprunt=empruntService.getListCurrentByMembre(id);
            request.setAttribute("listEmprunt", listEmprunt);
            request.setAttribute("id", id);
            request.setAttribute("nom", membre.getNom());
            request.setAttribute("prenom", membre.getPrenom());
            request.setAttribute("adresse", membre.getAdresse());
            request.setAttribute("email", membre.getEmail());
            request.setAttribute("telephone", membre.getTelephone());

            this.getServletContext().getRequestDispatcher("/WEB-INF/View/membre_details.jsp").forward(request, response);

            
        } catch (Exception e) {
        	System.out.println(e.getMessage());
            throw new ServletException();
        }   
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            doGet(request, response);

            MembreService membreService = MembreServiceImpl.getInstance();
            int id=Integer.valueOf(request.getParameter("id"));
            String nom=request.getParameter("nom");
            String prenom=request.getParameter("prenom");
            String adresse=request.getParameter("adresse");
            String email=request.getParameter("email");
            String telephone=request.getParameter("telephone");

            membreService.update(new Membre(id, nom, prenom, adresse, email, telephone, Abonnement.BASIC));

            this.getServletContext().getRequestDispatcher("/WEB-INF/View/membre_details.jsp").forward(request, response);
            
        } catch (Exception e) {
            throw new ServletException();
        }
	}
}