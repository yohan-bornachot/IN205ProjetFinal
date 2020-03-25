package com.excilys.librarymanager.test;

import java.time.LocalDate;
import java.util.List;

import com.excilys.librarymanager.dao.*;
import com.excilys.librarymanager.modele.*;

public class DaoTest {
	
	public static void printHeader(String text) { // Methode utile pour l'affichage
		int width = 8;
		String lineText = "";
		for (int i=0; i<text.length()+2*(width+1); i++) {
			lineText += "#";
		}
		
		String sideText = "";
		for (int i=0; i<width; i++) {
			sideText += "#";
		}
		
		System.out.print("\n\n");
		System.out.println(lineText);
		System.out.println(sideText + " " + text + " " + sideText);
		System.out.println(lineText);
	}
	
	
	public static void main(String[] args) throws Exception{
		
		// ---------- Test implémentation dao livre ----------
		
		LivreDao livre = LivreDaoImpl.getInstance();
		List<Livre> livreList;
		
		try {
			printHeader("# # # # # # # # Tests d'implémentation de la dao pour les livres # # # # # # # #");
			printHeader("Cr�ation d'un livre");
			int idNewLivre = livre.create("Le livre de la jungle", "Rudyard Kipling","111-3141592654");
			
			printHeader("R�cup�ration d'un livre par son identifiant");
			Livre newLivre = livre.getById(idNewLivre);
			System.out.println(newLivre);
			
			printHeader("Mise �  jour d'un livre");
			newLivre.setIsbn("111-1111111111");
			livre.update(newLivre);
			
			printHeader("Liste de tous les livres");
			livreList = livre.getList();
			for (int i=0; i<livreList.size();i++) 
				System.out.println(livreList.get(i));
			
			printHeader("Comptage du nombre de livres");
			System.out.println("nombre de livres : " + livre.count());
			
			printHeader("Suppression d'un livre");
			livre.delete(idNewLivre);
			
			printHeader("Comptage du nombre de livres");
			System.out.println("nombre de livres : " + livre.count());
			
		} catch (Exception e) {
			
		System.out.println(e);
		
		}
		
	
		// ---------- Test implémentation dao membre ----------
		
		
		MembreDao membre = MembreDaoImpl.getInstance();
		List<Membre> membreList;
		
		try {
			printHeader("# # # # # # # # Tests d'impl�mentation de la dao pour les membres # # # # # # # #");
			printHeader("Cr�ation d'un membre");
			int idNewMembre = membre.create("Vador","Dark","13 bis rue de l'�toile noire","dark.vador@ensta-paris.fr","0101010101",Abonnement.VIP);
						
			printHeader("R�cup�ration d'un membre par son identifiant");
			Membre newMembre = membre.getById(idNewMembre);
			System.out.println(newMembre);
			
			printHeader("Mise �  jour d'un membre");
			newMembre.setAbonnement(Abonnement.BASIC);
			membre.update(newMembre);
			
			printHeader("Liste de tous les membres");
			membreList = membre.getList();
			for (int i=0; i<membreList.size();i++) 
				System.out.println(membreList.get(i));
			
			printHeader("Comptage du nombre de membres");
			System.out.println("nombre de membres : " + membre.count());
			
			printHeader("Suppression d'un membre");
			membre.delete(idNewMembre);
			
			printHeader("Comptage du nombre de membres");
			System.out.println("nombre de membres : " + membre.count());
			
		} catch (Exception e) {
			
		System.out.println(e);
		
		}
		
		// ---------- Test implémentation dao emprunt ----------
		
		EmpruntDao emprunt = EmpruntDaoImpl.getInstance();
		List<Emprunt> empruntList;
		
		try {
			printHeader("# # # # # # # # Tests d'impl�mentation de la dao pour les emprunts # # # # # # # #");

			printHeader("Comptage du nombre d'emprunts");
			System.out.println("nombre d'emprunts : " + emprunt.count());
			
			printHeader("Cr�ation d'un emprunt");
			int idNewEmprunt = emprunt.create(1, 10, LocalDate.of(1998, 11, 13));
			
			printHeader("R�cup�ration d'un emprunt par son identifiant");
			Emprunt newEmprunt = emprunt.getById(idNewEmprunt);
			System.out.println(newEmprunt);
			
			printHeader("Comptage du nombre d'emprunts");
			System.out.println("nombre d'emprunts : " + emprunt.count());
			
			printHeader("Mise �  jour d'un emprunt");
			newEmprunt.setDateRetour(LocalDate.now());
			emprunt.update(newEmprunt);
			
			printHeader("Liste de tous les emprunts");
			empruntList = emprunt.getList();
			for (int i=0; i<empruntList.size();i++) 
				System.out.println(empruntList.get(i));

			
			printHeader("Liste des emprunts pas encore rendus");
			empruntList = emprunt.getListCurrent();
			for (int i=0; i<empruntList.size();i++)
				System.out.println(empruntList.get(i));
			
			printHeader("Liste des emprunts pas encore rendus pour un membre donn�");
			empruntList = emprunt.getListCurrentByMembre(4);
			for (int i=0; i<empruntList.size();i++)
				System.out.println(empruntList.get(i));
			
			printHeader("Liste des emprunts pas encore rendus pour un livre donn�");
			empruntList = emprunt.getListCurrentByLivre(8);
			for (int i=0; i<empruntList.size();i++)
				System.out.println(empruntList.get(i));
			

			
		} catch (Exception e) {
			System.out.println(e);
		}
		
		
	}
}