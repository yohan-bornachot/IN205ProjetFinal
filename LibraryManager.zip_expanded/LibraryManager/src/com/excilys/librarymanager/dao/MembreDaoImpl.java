package com.excilys.librarymanager.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.excilys.librarymanager.exception.DaoException;
import com.excilys.librarymanager.modele.Abonnement;
import com.excilys.librarymanager.modele.Membre;
import com.excilys.librarymanager.persistence.ConnectionManager;

public class MembreDaoImpl implements MembreDao{
	private static MembreDao instance;
	private MembreDaoImpl() { }	
	public static MembreDao getInstance() {
		if(instance == null) {
			instance = new MembreDaoImpl();
		}
		return instance;
	}
	
	
	private static final String GET_LIST_QUERY = "SELECT id, nom, prenom, adresse, email, telephone, abonnement\r\n" + 
			"FROM membre\r\n" + 
			"ORDER BY nom, prenom;";
	private static final String GET_BY_ID_QUERY = "SELECT id, nom, prenom, adresse, email, telephone, abonnement\r\n" + 
			"FROM membre\r\n" + 
			"WHERE id = ?;";
	private static final String CREATE_QUERY = "INSERT INTO membre(nom, prenom, adresse, email, telephone,\r\n" + 
			"abonnement)\r\n" + 
			"VALUES (?, ?, ?, ?, ?, ?);";
	private static final String UPDATE_QUERY = "UPDATE membre\r\n" + 
			"SET nom = ?, prenom = ?, adresse = ?, email = ?, telephone = ?,\r\n" + 
			"abonnement = ?\r\n" + 
			"WHERE id = ?;";
	private static final String DELETE_QUERY = "DELETE FROM membre WHERE id = ?;";
	private static final String COUNT_QUERY = "SELECT COUNT(id) AS count FROM membre;";
	
	@Override
	public List<Membre> getList() throws DaoException{
		
		List<Membre> membreList = new ArrayList<Membre>();
		try(
				Connection connection = ConnectionManager.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(GET_LIST_QUERY);
				ResultSet res = preparedStatement.executeQuery();
				)
		{
			while(res.next()){
				Membre membre = new Membre();
				membre.setId(res.getInt("id"));
				membre.setNom(res.getString("nom"));
				membre.setPrenom(res.getString("prenom"));
				membre.setAdresse(res.getString("adresse"));
				membre.setEmail(res.getString("email"));
				membre.setTelephone(res.getString("telephone"));
				membre.setAbonnement(Abonnement.valueOf(res.getInt("abonnement")));
				
				membreList.add(membre);
			}

		} catch (SQLException e) {
			throw new DaoException("Problème lors de la récupération de la liste des membres.");
		} 
		return membreList;
	}
	
	
	@Override
	public Membre getById(int id) throws DaoException {
		Membre membre = new Membre();
		try (
				Connection connection = ConnectionManager.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(GET_BY_ID_QUERY);
				ResultSet res = prepareQueryforGetID(id, preparedStatement);
				)
		{
			while(res.next()) {
				membre.setId(res.getInt("id"));
				membre.setNom(res.getString("nom"));
				membre.setPrenom(res.getString("prenom"));
				membre.setAdresse(res.getString("adresse"));
				membre.setEmail(res.getString("email"));
				membre.setTelephone(res.getString("telephone"));
				membre.setAbonnement(Abonnement.valueOf(res.getInt("abonnement")));
			}
			
			System.out.println("GET: " + membre.toString());
		} catch (SQLException e) {
			throw new DaoException("Problème lors de la récupération du membre");
		}
		return membre;
	}
	
	private ResultSet prepareQueryforGetID(int id, PreparedStatement preparedStatement) throws SQLException {
		preparedStatement.setInt(1, id);
		ResultSet res = preparedStatement.executeQuery();
		return res;
	}
	
	
	@Override
	public int create(String nom, String prenom, String adresse, String email, String telephone) throws DaoException {
		int id = -1;
		try (		
				Connection connection = ConnectionManager.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(CREATE_QUERY, Statement.RETURN_GENERATED_KEYS);
				ResultSet res = prepareQueryforCreate(nom, prenom, adresse, email, telephone, preparedStatement);
				)
		{
			if(res.next()){
				id = res.getInt(1);				
			}

			System.out.println("CREATE: membre [ id = " +  id + "nom = " + nom + " auteur = " + prenom + "adresse = " + adresse + "email =" + email + "telephone = " + telephone + "].");
		} catch (SQLException e) {
			throw new DaoException("Problème lors de la création du membre");
		}
		return id;
	}
	
	private ResultSet prepareQueryforCreate(String nom, String prenom, String adresse, String email, String telephone, PreparedStatement preparedStatement) throws SQLException {
		preparedStatement.setString(1, nom);
		preparedStatement.setString(2, prenom);
		preparedStatement.setString(3, adresse);
		preparedStatement.setString(4, email);
		preparedStatement.setString(5, telephone);
		preparedStatement.executeUpdate();
		ResultSet res = preparedStatement.getGeneratedKeys();
		return res;
	}
	
	@Override
	public void update(Membre membre) throws DaoException {
;
		try (
				Connection connection = ConnectionManager.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_QUERY);
			)
		{
			preparedStatement.setString(1, membre.getNom());
			preparedStatement.setString(2, membre.getPrenom());
			preparedStatement.setString(3, membre.getAdresse());
			preparedStatement.setString(4, membre.getEmail());
			preparedStatement.setString(5, membre.getTelephone());
			preparedStatement.setString(6, membre.getAdresse());
			preparedStatement.setInt(7, membre.getId());
			preparedStatement.executeUpdate();

			System.out.println("UPDATE: " + membre.toString());
		} catch (SQLException e) {
			throw new DaoException("Problème lors de la mise à jour du membre");
		}
	}
	
	@Override
	public void delete(int id) throws DaoException {
		try (
				Connection connection = ConnectionManager.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(DELETE_QUERY);)
		{
			preparedStatement.setInt(1, id);
			preparedStatement.executeUpdate();
			preparedStatement.close();
			connection.close();
			System.out.println("DELETE: " + id);
		} catch (SQLException e) {
			throw new DaoException("Problème lors de la suppression du membre :" + id );
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
			throw new DaoException("Problème lors du comptage des membres :");
		}
		return count;
	}
}
