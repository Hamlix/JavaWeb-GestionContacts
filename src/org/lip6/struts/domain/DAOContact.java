package org.lip6.struts.domain;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DAOContact {

	private String driver = "com.mysql.jdbc.Driver";
	private String url = "jdbc:mysql://localhost:3306/gestioncontacts";
	private String uid = "root";
	private String passwd = "root";

	public String addContact(final long id, final String firstName, final String lastName, final String email) {

		Connection cx = null;
		Statement stmt = null;

		System.out.println("Entre dans createContact");
		try {
			Class.forName(driver);
			cx = DriverManager.getConnection(url, uid, passwd);

			// faire QQChose avec la connexion

			stmt = cx.createStatement();
			String requete = "INSERT INTO contact VALUES('" + id + "', '" + lastName + "', '" + firstName + "', '"
					+ email + "');";
			int nb = stmt.executeUpdate(requete);
			System.out.println("Nombre de lignes mises � jour : " + nb);
			
			return null;
		} catch (ClassNotFoundException e) {
			// classe du pilote introuvable
			return "NamingException : " + e.getMessage();
		} catch (SQLException e) {
			// acc�s � la base refus�
			return "SQLException : " + e.getMessage();
		}
	}
}
