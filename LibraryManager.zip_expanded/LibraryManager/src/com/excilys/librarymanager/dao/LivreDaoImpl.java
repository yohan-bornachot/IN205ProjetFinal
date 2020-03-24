package com.excilys.librarymanager.dao;

import java.sql.SQLException;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import com.excilys.librarymanager.exception.DaoException;
import com.excilys.librarymanager.modele.Livre;
import com.excilys.librarymanager.persistence.ConnectionManager;



public class LivreDaoImpl implements LivreDao{
	
	// --- Mise en place du design pattern Singleton ---

	private static LivreDao instance;
	private LivreDaoImpl() { }	
	public static LivreDao getInstance() {
		if(instance == null) {
			instance = new LivreDaoImpl();
		}
		return instance;
	}
	
	
	// --- RequÃªtes SQL utilisÃ©es ---

	private static final String GET_LIST_QUERY = "SELECT id, titre, auteur, isbn FROM livre;";
	private static final String GET_BY_ID_QUERY = "SELECT id, titre, auteur, isbn FROM livre WHERE id = ?;";
	private static final String CREATE_QUERY = "INSERT INTO livre (titre, auteur, isbn) VALUES (?, ?, ?);";
	private static final String UPDATE_QUERY = "UPDATE livre SET titre = ?, auteur = ?, isbn = ? WHERE id = ?;";
	private static final String DELETE_QUERY = "DELETE FROM livre WHERE id=?;";
	private static final String COUNT_QUERY = "SELECT COUNT(id) AS count FROM livre;";
	
	
	/*************************
	 *  Methods implemented  *
	 *************************/
	
	@Override
	public List<Livre> getList() throws DaoException{
		
		List<Livre> livreList = new ArrayList<Livre>();
		try(
				Connection connection = ConnectionManager.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(GET_LIST_QUERY);
				ResultSet res = preparedStatement.executeQuery();
				)
		{
			while(res.next()){
				Livre livre = new Livre();
				livre.setId(res.getInt("id"));
				livre.setTitre(res.getString("titre"));
				livre.setAuteur(res.getString("auteur"));
				livre.setIsbn(res.getString("isbn"));
				
				livreList.add(livre);
			}

		} catch (SQLException e) {
			throw new DaoException("ProblÃ¨me lors de la rÃ©cupÃ©ration de la liste des livres.");
		} 
		System.out.println("GET: Tous les livres");
		return livreList;
	}
	
	
	@Override
	public Livre getById(int id) throws DaoException {
		Livre livre = new Livre();
		try (
				Connection connection = ConnectionManager.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(GET_BY_ID_QUERY);
				ResultSet res = prepareQueryforGetID(id, preparedStatement);
				)
		{
			while(res.next()) {
				livre.setId(res.getInt("id"));
				livre.setTitre(res.getString("titre"));
				livre.setAuteur(res.getString("auteur"));
				livre.setIsbn(res.getString("isbn"));
			}
			
			System.out.println("GET: livre " + livre.getId());
		} catch (SQLException e) {
			throw new DaoException("ProblÃ¨me lors de la rÃ©cupÃ©ration du livre");
		}
		return livre;
	}
	
	
	
	private ResultSet prepareQueryforGetID(int id, PreparedStatement preparedStatement) throws SQLException {
		preparedStatement.setInt(1, id);
		ResultSet res = preparedStatement.executeQuery();
		return res;
	}
	
	
	
	@Override
	public int create(String titre, String auteur, String isbn) throws DaoException {
		int id = -1;
		try (		
				Connection connection = ConnectionManager.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(CREATE_QUERY, Statement.RETURN_GENERATED_KEYS);
				ResultSet res = prepareQueryforCreate(titre, auteur, isbn, preparedStatement);
				)
		{
			if(res.next()){
				id = res.getInt(1);				
			}

			System.out.println("CREATE: livre " + id);
		} catch (SQLException e) {
			throw new DaoException("ProblÃ¨me lors de la crÃ©ation du livre");
		}
		return id;
	}
	
	
	
	private ResultSet prepareQueryforCreate(String titre, String auteur, String isbn,
			PreparedStatement preparedStatement) throws SQLException {
		preparedStatement.setString(1, titre);
		preparedStatement.setString(2, auteur);
		preparedStatement.setString(3, isbn);
		preparedStatement.executeUpdate();
		ResultSet res = preparedStatement.getGeneratedKeys();
		return res;
	}
	
	
	
	@Override
	public void update(Livre livre) throws DaoException {
;
		try (
				Connection connection = ConnectionManager.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_QUERY);
			)
		{
			preparedStatement.setString(1, livre.getTitre());
			preparedStatement.setString(2, livre.getAuteur());
			preparedStatement.setString(3, livre.getIsbn());
			preparedStatement.setInt(4, livre.getId());
			preparedStatement.executeUpdate();

			System.out.println("UPDATE: livre " + livre.getId());
		} catch (SQLException e) {
			throw new DaoException("ProblÃ¨me lors de la mise Ã  jour du livre");
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
			System.out.println("DELETE: livre " + id);
		} catch (SQLException e) {
			throw new DaoException("ProblÃ¨me lors de la suppression du livre:" + id );
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
		} catch (SQLException e) {
			throw new DaoException("ProblÃ¨me lors du comptage des livres :");
		}
		
		System.out.println("COUNT : "+ count +" livres");
		return count;
	}
}