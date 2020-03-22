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
	private static EmpruntDao instance;
	private EmpruntDaoImpl() { }	
	public static EmpruntDao getInstance() {
		if(instance == null) {
			instance = new EmpruntDaoImpl();
		}
		return instance;
	}
	
	
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
			"WHERE dateRetour IS NULL AND membre.id = ?\r\n" + 
			"WHERE dateRetour IS NULL AND livre.id= ?;";
	private static final String GET_LIST_CURRENT_BY_LIVRE_QUERY = "SELECT e.id AS id, idMembre, nom, prenom, adresse, email,\r\n" + 
			"telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt,\r\n" + 
			"dateRetour\r\n" + 
			"FROM emprunt AS e\r\n" + 
			"INNER JOIN membre ON membre.id = e.idMembre\r\n" + 
			"INNER JOIN livre ON livre.id = e.idLivre;";
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
				emprunt.setDateRetour(res.getDate("dateRetour").toLocalDate());
				
				empruntList.add(emprunt);
			}

		} catch (SQLException e) {
			throw new DaoException("Problème lors de la récupération de la liste des emprunts.");
		} 
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
				
				empruntList.add(emprunt);
			}

		} catch (SQLException e) {
			throw new DaoException("Problème lors de la récupération de la liste des emprunts en cours.");
		} 
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
				
				empruntList.add(emprunt);
			}

		} catch (SQLException e) {
			throw new DaoException("Problème lors de la récupération de la liste des membres.");
		} 
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
				
				empruntList.add(emprunt);
			}

		} catch (SQLException e) {
			throw new DaoException("Problème lors de la récupération de la liste des livres.");
		} 
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
			}
			
			System.out.println("GET: " + emprunt.toString());
		} catch (SQLException e) {
			throw new DaoException("Problème lors de la récupération de l'emprunt : " + id);
		}
		return emprunt;
	}
	
	private ResultSet prepareQueryforGetID(int id, PreparedStatement preparedStatement) throws SQLException {
		preparedStatement.setInt(1, id);
		ResultSet res = preparedStatement.executeQuery();
		return res;
	}
	
	@Override
	public void create(int idMembre, int idLivre, LocalDate dateEmprunt) throws DaoException {
		int id=-1;
		try (		
				Connection connection = ConnectionManager.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(CREATE_QUERY, Statement.RETURN_GENERATED_KEYS);
				ResultSet res = prepareQueryforCreate(idMembre, idLivre, dateEmprunt, preparedStatement);
				)
		{
			if(res.next()){
				id = res.getInt(1);				
			}

			System.out.println("CREATE: emprunt");
		} catch (SQLException e) {
			throw new DaoException("Problème lors de la création de l'emprunt");
		}
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
			preparedStatement.executeUpdate();

			System.out.println("UPDATE: " + emprunt.toString());
		} catch (SQLException e) {
			throw new DaoException("Problème lors de la mise à jour de l'emprunt");
		}
	}
	
	
	@Override
	public int count() throws DaoException {
		int count = -1;
		try (
				Connection connection = ConnectionManager.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(COUNT_QUERY);
				ResultSet res = preparedStatement.executeQuery();)
		{
			if(res.next()) {
				count = res.getInt(1);
			}
			System.out.println("COUNT : " + count);
		} catch (SQLException e) {
			throw new DaoException("Problème lors du comptage des Emprunts :");
		}
		return count;
	}
}
