package com.excilys.librarymanager.modele;

import java.time.LocalDate;


public class Emprunt {
	
	/****************
	 *  Attributes  *
	 ****************/
	
	private int id;
	private Membre membre;
	private Livre livre;
	private LocalDate dateEmprunt;
	private LocalDate dateRetour;

	
	/******************
	 *  Constructors  *
	 ******************/
	
	public Emprunt() {}
	
	public Emprunt(int id, Membre membre, Livre livre, LocalDate dateEmprunt, LocalDate dateRetour) {
		this.id = id;
		this.membre = membre;
		this.livre = livre;
		this.dateEmprunt = dateEmprunt;
		this.dateRetour = dateRetour;
	}
	

	/*************
	 *  Methods  *
	 *************/
	
	// --- Getters & Setters ---
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Membre getMembre() {
		return membre;
	}
	public void setMembre(Membre membre) {
		this.membre = membre;
	}
	public Livre getLivre() {
		return livre;
	}
	public void setLivre(Livre livre) {
		this.livre = livre;
	}
	public LocalDate getDateEmprunt() {
		return dateEmprunt;
	}
	public void setDateEmprunt(LocalDate dateEmprunt) {
		this.dateEmprunt = dateEmprunt;
	}
	public LocalDate getDateRetour() {
		return dateRetour;
	}
	public void setDateRetour(LocalDate dateRetour) {
		this.dateRetour = dateRetour;
	}
	
	// --- Other methods ---
	
	@Override
	public String toString() {
		if (dateRetour == null) {
			return "Info Emprunt : |> id : " + id + "\n"
					+ "               |> Membre : " + membre.getNom() + " " + membre.getPrenom() + " [idMembre = " + membre.getId() + "]\n"
					+ "               |> Livre : " + livre.getTitre() + " - " + livre.getAuteur() + " [idLivre = " + livre.getId() + "]\n"
					+ "               |> date d'emprunt : " + dateEmprunt + "\n"
					+ "               |> date de retour : livre pas encore rendu\n\n";
		}
		return "Info Emprunt : |> id : " + id + "\n"
	+ "               |> Membre : " + membre.getNom() + " " + membre.getPrenom() + " [idMembre = " + membre.getId() + "]\n"
	+ "               |> Livre : " + livre.getTitre() + " - " + livre.getAuteur() + " [idLivre = " + livre.getId() + "]\n"
	+ "               |> date d'emprunt : " + dateEmprunt + "\n"
	+ "               |> date de retour : " + dateRetour + "\n\n";
	 }
}
