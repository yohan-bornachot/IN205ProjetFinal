package com.excilys.librarymanager.modele;

public class Membre {
	
	/****************
	 *  Attributes  *
	 ****************/
	
	private int id;
	private String nom;
	private String prenom;
	private String adresse;
	private String email;
	private String telephone;
	private Abonnement abonnement;

	
	/******************
	 *  Constructors  *
	 ******************/
	
	public Membre() {}
	
	public Membre(int id, String nom, String prenom, String adresse, String email, String telephone, Abonnement abonnement) {
		this.id = id;
		this.nom = nom;
		this.prenom = prenom;
		this.adresse = adresse;
		this.email = email;
		this.telephone = telephone;
		this.abonnement = abonnement;
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
	
	public String getNom() {
		return nom;
	}
	
	public void setNom(String nom) {
		this.nom = nom;
	}
	
	public String getPrenom() {
		return prenom;
	}
	
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	
	public String getAdresse() {
		return adresse;
	}
	
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getTelephone() {
		return telephone;
	}
	
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	
	public Abonnement getAbonnement() {
		return abonnement;
	}
	
	public void setAbonnement(Abonnement abonnement) {
		this.abonnement = abonnement;
	}
	
	// --- Other methods ---

	@Override
	public String toString() {
		return "Info Membre : |> id : " + id + "\n"
				+ "              |> nom : " + nom + "\n"
				+ "              |> prenom : " + prenom + "\n"
				+ "              |> adresse : " + adresse + "\n"
				+ "              |> email : " + email + "\n"
				+ "              |> telephone : " + telephone + "\n"
				+ "              |> abonnement : " + abonnement + "\n\n";
	}
	
}