package com.excilys.librarymanager.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.excilys.librarymanager.exception.DaoException;
import com.excilys.librarymanager.modele.Abonnement;
import com.excilys.librarymanager.modele.Emprunt;
import com.excilys.librarymanager.modele.Membre;
import com.excilys.librarymanager.modele.Livre;
import com.excilys.librarymanager.persistence.ConnectionManager;


public class EmpruntDaoImpl implements EmpruntDao{
	
	// --- Mise en place du design pattern Singleton ---
	
	private static EmpruntDao instance;
	
	private EmpruntDaoImpl() { }	
	public static EmpruntDao getInstance() {
		if(instance == null) {
			instance = new EmpruntDaoImpl();
		}
		return instance;
	}
	
	
	// --- Requêtes SQL utilisées ---
	
	private static final String GET_LIST_QUERY = "SELECT e.id AS id, idMembre, nom, prenom, adresse, email, telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt, dateRetour\r\n" +
			"FROM emprunt AS e \r\n" +
			"INNER JOIN membre ON membre.id = e.idMembre \r\n" +
			"INNER JOIN livre ON livre.id = e.idLivre \r\n" +
			"ORDER BY dateRetour DESC;";
	private static final String GET_LIST_CURRENT_QUERY = "SELECT e.id AS id, idMembre, nom, prenom, adresse, email,\r\n" + 
			"telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt,\r\n" + 
			"dateRetour\r\n" + 
			"FROM emprunt AS e\r\n" + 
			"INNER JOIN membre ON membre.id = e.idMembre\r\n" + 
			"INNER JOIN livre ON livre.id = e.idLivre\r\n" + 
			"WHERE dateRetour IS NULL;";
	private static final String GET_LIST_CURRENT_BY_MEMBRE_QUERY = "SELECT e.id AS id, idMembre, nom, prenom, adresse, email,\r\n" + 
			"telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt,\r\n" + 
			"dateRetour\r\n" + 
			"FROM emprunt AS e\r\n" + 
			"INNER JOIN membre ON membre.id = e.idMembre\r\n" + 
			"INNER JOIN livre ON livre.id = e.idLivre\r\n" + 
			"WHERE dateRetour IS NULL AND membre.id = ?;";
	private static final String GET_LIST_CURRENT_BY_LIVRE_QUERY = "SELECT e.id AS id, idMembre, nom, prenom, adresse, email,\r\n" + 
			"telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt,\r\n" + 
			"dateRetour\r\n" + 
			"FROM emprunt AS e\r\n" + 
			"INNER JOIN membre ON membre.id = e.idMembre\r\n" + 
			"INNER JOIN livre ON livre.id = e.idLivre\r\n"+
			"WHERE dateRetour IS NULL AND livre.id = ?;";
	private static final String GET_BY_ID_QUERY = "SELECT e.id AS idEmprunt, idMembre, nom, prenom, adresse, email,\r\n" + 
			"telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt,\r\n" + 
			"dateRetour\r\n" + 
			"FROM emprunt AS e\r\n" + 
			"INNER JOIN membre ON membre.id = e.idMembre\r\n" + 
			"INNER JOIN livre ON livre.id = e.idLivre\r\n" + 
			"WHERE e.id = ?;";
	private static final String CREATE_QUERY = "INSERT INTO emprunt(idMembre, idLivre, dateEmprunt, dateRetour)\r\n" + 
			"VALUES (?, ?, ?, ?);";
	private static final String UPDATE_QUERY = "UPDATE emprunt\r\n" + 
			"SET idMembre = ?, idLivre = ?, dateEmprunt = ?, dateRetour = ?\r\n" + 
			"WHERE id = ?;";
	private static final String COUNT_QUERY = "SELECT COUNT(id) AS count FROM emprunt;";
	
	
	/*************************
	 *  Methods implemented  *
	 *************************/
	
	@Override
	public List<Emprunt> getList() throws DaoException{
		
		List<Emprunt> empruntList = new ArrayList<Emprunt>();
		
		try(
				Connection connection = ConnectionManager.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(GET_LIST_QUERY);
				ResultSet res = preparedStatement.executeQuery();
				)
		{
			while(res.next()){
				Emprunt emprunt = new Emprunt();
				Membre membre = new Membre();
				Livre livre = new Livre();
				
				membre.setId(res.getInt("idMembre"));
				membre.setNom(res.getString("nom"));
				membre.setPrenom(res.getString("prenom"));
				membre.setAdresse(res.getString("adresse"));
				membre.setEmail(res.getString("email"));
				membre.setTelephone(res.getString("telephone"));
				membre.setAbonnement(Abonnement.valueOf(res.getInt("abonnement")));
				
				livre.setId(res.getInt("idLivre"));
				livre.setTitre(res.getString("titre"));
				livre.setAuteur(res.getString("auteur"));
				livre.setIsbn(res.getString("isbn"));
				
				emprunt.setId(res.getInt("id"));
				emprunt.setMembre(membre);
				emprunt.setLivre(livre);
				emprunt.setDateEmprunt(res.getDate("dateEmprunt").toLocalDate());
				if (res.getDate("dateRetour")!=null)
					emprunt.setDateRetour(res.getDate("dateRetour").toLocalDate());
				else 
					emprunt.setDateRetour(null);
				

				empruntList.add(emprunt);
			}

		} catch (SQLException e) {
			throw new DaoException("Problème lors de la récupération de la liste des emprunts.");
		} 
		System.out.println("GET: Tous les emprunts");
		return empruntList;
	}
	
	
	
	public List<Emprunt> getListCurrent() throws DaoException{
		
		List<Emprunt> empruntList = new ArrayList<Emprunt>();
		
		try(
				Connection connection = ConnectionManager.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(GET_LIST_CURRENT_QUERY);
				ResultSet res = preparedStatement.executeQuery();
				)
		{
			while(res.next()){
				Emprunt emprunt = new Emprunt();
				Membre membre = new Membre();
				Livre livre = new Livre();
				
				membre.setId(res.getInt("idMembre"));
				membre.setNom(res.getString("nom"));
				membre.setPrenom(res.getString("prenom"));
				membre.setAdresse(res.getString("adresse"));
				membre.setEmail(res.getString("email"));
				membre.setTelephone(res.getString("telephone"));
				membre.setAbonnement(Abonnement.valueOf(res.getInt("abonnement")));
				
				livre.setId(res.getInt("idLivre"));
				livre.setTitre(res.getString("titre"));
				livre.setAuteur(res.getString("auteur"));
				livre.setIsbn(res.getString("isbn"));
				
				emprunt.setId(res.getInt("id"));
				emprunt.setMembre(membre);
				emprunt.setLivre(livre);
				emprunt.setDateEmprunt(res.getDate("dateEmprunt").toLocalDate());
				// Forcément on a dateRetour == null car l'emprunt n'a pas été rendu. 
				// Ici dateRetour est initialisé �  null par le constructeur Emprunt().
			
				
				empruntList.add(emprunt);
				
			}

		} catch (SQLException e) {
			throw new DaoException("Problème lors de la récupération de la liste des emprunts en cours.");
		} 
		System.out.println("GET: Tous les emprunts non rendus");
		return empruntList;
	}
	
	
	
	public List<Emprunt> getListCurrentByMembre(int idMembre) throws DaoException{
		
		List<Emprunt> empruntList = new ArrayList<Emprunt>();
		
		try(
				Connection connection = ConnectionManager.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(GET_LIST_CURRENT_BY_MEMBRE_QUERY);
				ResultSet res = prepareQueryforGetID(idMembre, preparedStatement);
				)
		{
			while(res.next()){
				Emprunt emprunt = new Emprunt();
				Membre membre = new Membre();
				Livre livre = new Livre();
				
				membre.setId(res.getInt("idMembre"));
				membre.setNom(res.getString("nom"));
				membre.setPrenom(res.getString("prenom"));
				membre.setAdresse(res.getString("adresse"));
				membre.setEmail(res.getString("email"));
				membre.setTelephone(res.getString("telephone"));
				membre.setAbonnement(Abonnement.valueOf(res.getInt("abonnement")));
				
				livre.setId(res.getInt("idLivre"));
				livre.setTitre(res.getString("titre"));
				livre.setAuteur(res.getString("auteur"));
				livre.setIsbn(res.getString("isbn"));
				
				emprunt.setId(res.getInt("id"));
				emprunt.setMembre(membre);
				emprunt.setLivre(livre);
				emprunt.setDateEmprunt(res.getDate("dateEmprunt").toLocalDate());
				// Forcément on a dateRetour == null car l'emprunt n'a pas été rendu. 
				// Ici dateRetour est initialisé �  null par le constructeur Emprunt().
				
				empruntList.add(emprunt);
			}


		} catch (SQLException e) {
			throw new DaoException("Problème lors de la récupération de la liste des membres.");
		} 
		System.out.println("GET: Tous les emprunts non rendus pour membre " + idMembre);
		return empruntList;
	}
	
	
	
	public List<Emprunt> getListCurrentByLivre(int idLivre) throws DaoException{
		
		List<Emprunt> empruntList = new ArrayList<Emprunt>();
		
		try(
				Connection connection = ConnectionManager.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(GET_LIST_CURRENT_BY_LIVRE_QUERY);
				ResultSet res = prepareQueryforGetID(idLivre, preparedStatement);
				)
		{
			while(res.next()){
				
				Emprunt emprunt = new Emprunt();
				Membre membre = new Membre();
				Livre livre = new Livre();
				
				membre.setId(res.getInt("idMembre"));
				membre.setNom(res.getString("nom"));
				membre.setPrenom(res.getString("prenom"));
				membre.setAdresse(res.getString("adresse"));
				membre.setEmail(res.getString("email"));
				membre.setTelephone(res.getString("telephone"));
				membre.setAbonnement(Abonnement.valueOf(res.getInt("abonnement")));
				
				livre.setId(res.getInt("idLivre"));
				livre.setTitre(res.getString("titre"));
				livre.setAuteur(res.getString("auteur"));
				livre.setIsbn(res.getString("isbn"));
				
				emprunt.setId(res.getInt("id"));
				emprunt.setMembre(membre);
				emprunt.setLivre(livre);
				emprunt.setDateEmprunt(res.getDate("dateEmprunt").toLocalDate());
				// Forcément on a dateRetour == null car l'emprunt n'a pas été rendu. 
				// Ici dateRetour est initialisé �  null par le constructeur Emprunt().
				
				empruntList.add(emprunt);
			}

		} catch (SQLException e) {
			throw new DaoException("Problème lors de la récupération de la liste des livres.");
		} 
		System.out.println("GET: Tous les emprunts non rendus pour livre " + idLivre);
		return empruntList;
	}
	
	
	
	@Override
	public Emprunt getById(int id) throws DaoException {
		
		Emprunt emprunt = new Emprunt();
		
		try (
				Connection connection = ConnectionManager.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(GET_BY_ID_QUERY);
				ResultSet res = prepareQueryforGetID(id, preparedStatement);
				)
		{
			while(res.next()) {
				Membre membre = new Membre();
				Livre livre = new Livre();
				
				membre.setId(res.getInt("idMembre"));
				membre.setNom(res.getString("nom"));
				membre.setPrenom(res.getString("prenom"));
				membre.setAdresse(res.getString("adresse"));
				membre.setEmail(res.getString("email"));
				membre.setTelephone(res.getString("telephone"));
				membre.setAbonnement(Abonnement.valueOf(res.getInt("abonnement")));
				
				livre.setId(res.getInt("idLivre"));
				livre.setTitre(res.getString("titre"));
				livre.setAuteur(res.getString("auteur"));
				livre.setIsbn(res.getString("isbn"));
				
				emprunt.setId(res.getInt("id"));
				emprunt.setMembre(membre);
				emprunt.setLivre(livre);
				emprunt.setDateEmprunt(res.getDate("dateEmprunt").toLocalDate());
				Date dateRetour = res.getDate("DateRetour");
				if(dateRetour == null)
					emprunt.setDateRetour(null);
				else	
					emprunt.setDateRetour(res.getDate("dateRetour").toLocalDate());

			}
			
			
		} catch (SQLException e) {
			throw new DaoException("Problème lors de la récupération de l'emprunt : " + id);
		}
		System.out.println("GET: emprunt " + emprunt.getId());
		return emprunt;
	}
	
	
	
	private ResultSet prepareQueryforGetID(int id, PreparedStatement preparedStatement) throws SQLException {
		preparedStatement.setInt(1, id);
		ResultSet res = preparedStatement.executeQuery();
		return res;
	}
	
	
	
	@Override
	public int create(int idMembre, int idLivre, LocalDate dateEmprunt) throws DaoException {
		int id=-1;
		try (		
				Connection connection = ConnectionManager.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(CREATE_QUERY, Statement.RETURN_GENERATED_KEYS);
				ResultSet res = prepareQueryforCreate(idMembre, idLivre, dateEmprunt, preparedStatement);
				)
		{
			if(res.next()){
				id = res.getInt(1);	
				System.out.println("CREATE: emprunt "+id);

			}
		} catch (SQLException e) {
			throw new DaoException("Problème lors de la création de l'emprunt");
		}
		return id;
	}
	
	
	
	private ResultSet prepareQueryforCreate(int idMembre, int idLivre, LocalDate dateEmprunt, PreparedStatement preparedStatement) throws SQLException {
		preparedStatement.setInt(1, idMembre);
		preparedStatement.setInt(2, idLivre);
		preparedStatement.setDate(3, Date.valueOf(dateEmprunt));
		preparedStatement.setDate(4, null);
		preparedStatement.executeUpdate();
		ResultSet res = preparedStatement.getGeneratedKeys();
		return res;
	}
	

	
	@Override
	public void update(Emprunt emprunt) throws DaoException {
;
		try (
				Connection connection = ConnectionManager.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_QUERY);
			)
		{
			preparedStatement.setInt(1, emprunt.getMembre().getId());
			preparedStatement.setInt(2, emprunt.getLivre().getId());
			preparedStatement.setDate(3, Date.valueOf(emprunt.getDateEmprunt()));
			preparedStatement.setDate(4, Date.valueOf(emprunt.getDateRetour()));
			preparedStatement.setInt(5, emprunt.getId());
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			throw new DaoException("Problème lors de la mise �  jour de l'emprunt");
		}
		
		System.out.println("UPDATE: emprunt " + emprunt.getId());

	}
	
	
	@Override
	public int count() throws DaoException {
		int count =-1;
		try (
				Connection connection = ConnectionManager.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(COUNT_QUERY);
				ResultSet res = preparedStatement.executeQuery();)
		{
			if(res.next()) {
				count = res.getInt(1);

			}
		} catch (SQLException e) {
			throw new DaoException("Problème lors du comptage des Emprunts :");
		}
		System.out.println("COUNT: " + count + " emprunts");
		return count;
	}
}