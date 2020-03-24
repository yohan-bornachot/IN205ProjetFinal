package com.excilys.librarymanager.modele;

public class Livre {
	
	/****************
	 *  Attributes  *
	 ****************/
	
	private int id;
	private String titre;
	private String auteur;
	private String isbn;
	
	
	/******************
	 *  Constructors  *
	 ******************/
	
	public Livre() {}
	
	public Livre(int id, String titre, String auteur, String isbn) {
		this.id = id;
		this.titre = titre;
		this.auteur = auteur;
		this.isbn = isbn;
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
	public String getTitre() {
		return titre;
	}
	public void setTitre(String titre) {
		this.titre = titre;
	}
	public String getAuteur() {
		return auteur;
	}
	public void setAuteur(String auteur) {
		this.auteur = auteur;
	}
	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	
	// --- Other methods ---

	@Override
	public String toString() {
		return "Info Livre : |> id : " + id + "\n"
				+ "             |> titre : " + titre + "\n"
				+ "             |> auteur : " + auteur + "\n"
				+ "             |> isbn : " + isbn + "\n\n";
	}
}
