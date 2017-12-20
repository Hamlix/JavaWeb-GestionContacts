package org.lip6.struts.domain;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DAOContactOld {

	private final static String RESOURCE_JDBC = "java:comp/env/jdbc/gestioncontacts";

	public String addContact(final String firstName, final String lastName, final String email,
			final long idAddress, final String street, final String city, final String zip, final String country) {

		System.out.println("Entre dans creation contact DAO");

		Connection lConnection = null;

		try {
			final Context lContext = new InitialContext();
			final DataSource lDataSource = (DataSource) lContext.lookup(RESOURCE_JDBC);
			lConnection = lDataSource.getConnection();

			/* // On regarde si la combinaison email/nom existe
			final PreparedStatement lPreparedStatementContact =

					lConnection.prepareStatement("SELECT ID FROM contact WHERE LASTNAME = ? AND EMAIL = ?");

			lPreparedStatementContact.setString(1, lastName);
			lPreparedStatementContact.setString(2, email);

			ResultSet rsContact = lPreparedStatementContact.executeQuery();
			if (rsContact.next()) {
				return "La combinaison email/nom existe déjà !";
			}*/

			/*// On regarde si le email existe
			final PreparedStatement lPreparedStatementEmail =

					lConnection.prepareStatement("SELECT ID FROM contact WHERE EMAIL = ?");

			lPreparedStatementEmail.setString(1, email);
			ResultSet rsEmail = lPreparedStatementEmail.executeQuery();
			if (rsEmail.next()) {
				return "L'email existe déjà !";
			}*/

			// Contact
			final PreparedStatement lPreparedStatementCreation =

					lConnection
							.prepareStatement("INSERT INTO CONTACT(IdContact, LastName, FirstName, Email, ErrorContact) VALUES(null, ?, ?, ?,null)");

			lPreparedStatementCreation.setString(1, lastName);
			lPreparedStatementCreation.setString(2, firstName);
			lPreparedStatementCreation.setString(3, email);
			lPreparedStatementCreation.executeUpdate();

			// Address

			// Si l'un des attributs est non vide cela veut dire que les autres
			// non plus, c'est pourquoi on ne v�rifie que la rue
			if (!street.isEmpty()) {
				PreparedStatement lPreparedStatementAddressCreation =

						lConnection.prepareStatement(
								"INSERT INTO ADDRESS(IdAddress, Street, City, Zip, Country) VALUES(null, ?, ?, ?, ?)");

				lPreparedStatementAddressCreation.setString(1, street);
				lPreparedStatementAddressCreation.setString(2, city);
				lPreparedStatementAddressCreation.setString(3, zip);
				lPreparedStatementAddressCreation.setString(4, country);
				lPreparedStatementAddressCreation.executeUpdate();
			}

			return null;
		} catch (NamingException e) {

			return "NamingException : " + e.getMessage();

		} catch (SQLException e) {

			return "SQLException : " + e.getMessage();

		} finally {
			try {
				if (lConnection != null)
					lConnection.close();
			} catch (SQLException e) {
				return "Erreur : " + e.getMessage();
			}
		}
	}

	public DisplayAllContact displayAllContacts() {

		System.out.println("Entre dans affichage tous les contact DAO");

		Connection lConnection = null;
		final DisplayAllContact display = new DisplayAllContact();

		try {
			final Context lContext = new InitialContext();
			final DataSource lDataSource = (DataSource) lContext.lookup(RESOURCE_JDBC);
			lConnection = lDataSource.getConnection();

			final List<ContactEntity> contacts = new LinkedList<ContactEntity>();

			final PreparedStatement lPreparedStatementContact =

					lConnection.prepareStatement("SELECT IdContact, LastName, FirstName, Email FROM contact");

			ResultSet rsContact = lPreparedStatementContact.executeQuery();

			while (rsContact.next()) {
				final int id = rsContact.getInt("IdContact");
				final String lastName = rsContact.getString("LastName");
				final String firstName = rsContact.getString("FirstName");
				final String email = rsContact.getString("Email");

				contacts.add(new ContactEntity(id, lastName, firstName, email,null,null,null,null,null));
			}

			display.setContacts(contacts);

		} catch (NamingException e) {

			System.out.println(e.getMessage());
			display.setError("NamingException : " + e.getMessage());

		} catch (SQLException e) {

			System.out.println(e.getMessage());
			display.setError("SQLException : " + e.getMessage());

		} finally {
			try {
				if (lConnection != null)
					lConnection.close();
			} catch (SQLException e) {
				display.setError("Erreur : " + e.getMessage());
			}
		}
		return display;
	}

	public DisplayAllContact displayContact(int idContact) {

		System.out.println("Entre dans affichage contact DAO : " + idContact);

		Connection lConnection = null;
		final DisplayAllContact display = new DisplayAllContact();

		try {
			final Context lContext = new InitialContext();
			final DataSource lDataSource = (DataSource) lContext.lookup(RESOURCE_JDBC);
			lConnection = lDataSource.getConnection();

			final List<ContactEntity> contacts = new LinkedList<ContactEntity>();

			final PreparedStatement lPreparedStatementContact =

					lConnection.prepareStatement("SELECT IdContact, LastName, FirstName, Email FROM contact WHERE ID=?");

			lPreparedStatementContact.setLong(1, idContact);
			ResultSet rsContact = lPreparedStatementContact.executeQuery();

			while (rsContact.next()) {
				final int id = rsContact.getInt("IdContact");
				final String lastName = rsContact.getString("LastName");
				final String firstName = rsContact.getString("FirstName");
				final String email = rsContact.getString("Email");

				final List<AddressEntity> address = new LinkedList<AddressEntity>();

				final PreparedStatement lPreparedStatementAddress =

						lConnection.prepareStatement("SELECT IdAddress, Street, City, Zip, Country FROM address WHERE IdContact=?");

				lPreparedStatementAddress.setLong(1, idContact);
				ResultSet rsAddress = lPreparedStatementAddress.executeQuery();

				while (rsAddress.next()) {

					final String idAddress = rsAddress.getString("IdContact");
					final String street = rsAddress.getString("Street");
					final String city = rsAddress.getString("City");
					final String zip = rsAddress.getString("Zip");
					final String country = rsAddress.getString("Country");

					address.add(new AddressEntity(idAddress, street, city, zip, country));
				}

				final List<ContactgroupEntity> groups = new LinkedList<ContactgroupEntity>();

				final PreparedStatement lPreparedStatementGroup =

						lConnection.prepareStatement(
								"SELECT IdGroup, GroupName FROM contactgroup INNER JOIN groupcomposition "
										+ "ON groupcomposition.IDGROUP = contactgroup.GROUPID AND groupcomposition.IDCONTACT = ?");

				lPreparedStatementGroup.setInt(1, id);

				ResultSet rsGroup = lPreparedStatementGroup.executeQuery();

				while (rsGroup.next()) {
					final int groupId = rsGroup.getInt("GROUPID");
					final String groupName = rsGroup.getString("GROUPNAME");

					groups.add(new ContactGroup(groupId, groupName));
				}

				final List<PhoneNumber> phones = new LinkedList<PhoneNumber>();

				final PreparedStatement lPreparedStatementPhone =

						lConnection.prepareStatement(
								"SELECT ID, PHONEKIND, PHONENUMBER FROM PHONENUMBER WHERE IDCONTACT = ?");

				lPreparedStatementPhone.setInt(1, id);

				ResultSet rsPhone = lPreparedStatementPhone.executeQuery();

				while (rsPhone.next()) {
					final int idPhone = rsPhone.getInt("ID");
					final String phoneKind = rsPhone.getString("PHONEKIND");
					final String phoneNumber = rsPhone.getString("PHONENUMBER");

					phones.add(new PhoneNumber(idPhone, id, phoneKind, phoneNumber, null));
				}

				contacts.add(new Contact(id, firstName, lastName, email, address, phones, groups));
			}

			display.setContacts(contacts);

		} catch (

		NamingException e) {

			System.out.println(e.getMessage());
			display.setError("NamingException : " + e.getMessage());

		} catch (SQLException e) {

			System.out.println(e.getMessage());
			display.setError("SQLException : " + e.getMessage());

		} finally {
			try {
				if (lConnection != null)
					lConnection.close();
			} catch (SQLException e) {
				display.setError("Erreur : " + e.getMessage());
			}
		}
		return display;
	}

	public String deleteContact(long id) {

		System.out.println("Entre dans deleteContact");

		Connection lConnection = null;

		try {
			final Context lContext = new InitialContext();
			final DataSource lDataSource = (DataSource) lContext.lookup(RESOURCE_JDBC);
			lConnection = lDataSource.getConnection();

			// deleting a contact
			PreparedStatement lPreparedStatementDeletion =

					lConnection.prepareStatement("DELETE FROM contact WHERE ID=?");

			lPreparedStatementDeletion.setLong(1, id);
			lPreparedStatementDeletion.executeUpdate();

			PreparedStatement lPreparedStatementDeletionComposition = lConnection
					.prepareStatement("DELETE FROM GROUPCOMPOSITION WHERE IDCONTACT=?");

			lPreparedStatementDeletionComposition.setLong(1, id);
			lPreparedStatementDeletionComposition.executeUpdate();

			PreparedStatement lPreparedStatementDeletionAddress = lConnection
					.prepareStatement("DELETE FROM ADDRESS WHERE ID=?");

			lPreparedStatementDeletionAddress.setLong(1, id);
			lPreparedStatementDeletionAddress.executeUpdate();

			PreparedStatement lPreparedStatementDeletionPhone = lConnection
					.prepareStatement("DELETE FROM PHONENUMBER WHERE IDCONTACT=?");

			lPreparedStatementDeletionPhone.setLong(1, id);
			lPreparedStatementDeletionPhone.executeUpdate();

			return null;

		} catch (NamingException e) {

			return "NamingException : " + e.getMessage();

		} catch (SQLException e) {

			return "SQLException : " + e.getMessage();

		} finally {
			try {
				if (lConnection != null)
					lConnection.close();
			} catch (SQLException e) {
				return "Erreur : " + e.getMessage();
			}
		}
	}

	public String updateContact(final long id, final String lastName, final String firstName, final String email,
			final String street, final String city, final String zip, final String country) {

		Connection lConnection = null;

		try {
			final Context lContext = new InitialContext();
			final DataSource lDataSource = (DataSource) lContext.lookup(RESOURCE_JDBC);
			lConnection = lDataSource.getConnection();

			// On regarde si la combinaison email/nom existe
			final PreparedStatement lPreparedStatementContact =

					lConnection.prepareStatement("SELECT ID FROM contact WHERE LASTNAME = ? AND EMAIL = ?");

			lPreparedStatementContact.setString(1, lastName);
			lPreparedStatementContact.setString(2, email);

			ResultSet rsContact = lPreparedStatementContact.executeQuery();
			if (rsContact.next()) {
				
				//On regarde si le contact � update est le m�me contact (pour �viter la combinaison email/nom existe d�j� si on ne change rien)
				final int idContact = rsContact.getInt("ID");
				if(idContact != id) {
					return "La combinaison email/nom existe déjà !";
				}
			}

			// On regarde si le email existe
			final PreparedStatement lPreparedStatementEmail =

					lConnection.prepareStatement("SELECT ID FROM contact WHERE EMAIL = ?");

			lPreparedStatementEmail.setString(1, email);
			ResultSet rsEmail = lPreparedStatementEmail.executeQuery();
			if (rsEmail.next()) {
				
				final int idContact = rsEmail.getInt("ID");
				if(idContact != id) {
					return "L'email existe déjà !";
				}
			}

			PreparedStatement lPreparedStatementUpdate =

					lConnection.prepareStatement("UPDATE contact SET LASTNAME=?, FIRSTNAME=?, EMAIL=? WHERE id=?");

			lPreparedStatementUpdate.setString(1, lastName);
			lPreparedStatementUpdate.setString(2, firstName);
			lPreparedStatementUpdate.setString(3, email);
			lPreparedStatementUpdate.setLong(4, id);
			lPreparedStatementUpdate.executeUpdate();

			PreparedStatement lPreparedStatementAddressExist =

					lConnection.prepareStatement("SELECT * FROM address WHERE id=?");

			lPreparedStatementAddressExist.setLong(1, id);
			ResultSet rsAddress = lPreparedStatementAddressExist.executeQuery();

			if (!street.isEmpty() && rsAddress.next()) {
				PreparedStatement lPreparedStatementUpdateAddress =

						lConnection.prepareStatement(
								"UPDATE address SET address.ID=?, STREET=?, CITY=?, ZIP=?, COUNTRY=? WHERE id=?");

				lPreparedStatementUpdateAddress.setLong(1, id);
				lPreparedStatementUpdateAddress.setString(2, street);
				lPreparedStatementUpdateAddress.setString(3, city);
				lPreparedStatementUpdateAddress.setString(4, zip);
				lPreparedStatementUpdateAddress.setString(5, country);
				lPreparedStatementUpdateAddress.setLong(6, id);
				lPreparedStatementUpdateAddress.executeUpdate();
			} else if (!street.isEmpty() && !rsAddress.next()) {
				PreparedStatement lPreparedStatementUpdateAddress =

						lConnection.prepareStatement(
								"INSERT INTO ADDRESS(ID, STREET, CITY, ZIP, COUNTRY) VALUES(?, ?, ?, ?, ?)");

				lPreparedStatementUpdateAddress.setLong(1, id);
				lPreparedStatementUpdateAddress.setString(2, street);
				lPreparedStatementUpdateAddress.setString(3, city);
				lPreparedStatementUpdateAddress.setString(4, zip);
				lPreparedStatementUpdateAddress.setString(5, country);
				lPreparedStatementUpdateAddress.executeUpdate();
			} else {
				PreparedStatement lPreparedStatementDeletionAddress = lConnection
						.prepareStatement("DELETE FROM ADDRESS WHERE ID=?");

				lPreparedStatementDeletionAddress.setLong(1, id);
				lPreparedStatementDeletionAddress.executeUpdate();
			}

			return null;

		} catch (NamingException e) {

			return "NamingException : " + e.getMessage();

		} catch (SQLException e) {

			return "SQLException : " + e.getMessage();

		} finally {
			try {
				if (lConnection != null)
					lConnection.close();
			} catch (SQLException e) {
				return "Erreur : " + e.getMessage();
			}
		}
	}

	public DisplayAllContact searchContact(String word) {

		System.out.println("Entre dans search contact DAO");

		final DisplayAllContact display = new DisplayAllContact();
		Connection lConnection = null;

		try {
			final Context lContext = new InitialContext();
			final DataSource lDataSource = (DataSource) lContext.lookup(RESOURCE_JDBC);
			lConnection = lDataSource.getConnection();

			final List<Contact> contacts = new LinkedList<Contact>();

			word = word.replace("!", "!!").replace("%", "!%").replace("_", "!_").replace("[", "![");

			final PreparedStatement lPreparedStatementContact =

					lConnection.prepareStatement(
							"SELECT ID, LASTNAME, FIRSTNAME, EMAIL FROM contact WHERE ID LIKE ? OR LASTNAME LIKE ? OR FIRSTNAME LIKE ? OR EMAIL LIKE ?");

			lPreparedStatementContact.setString(1, "%" + word + "%");
			lPreparedStatementContact.setString(2, "%" + word + "%");
			lPreparedStatementContact.setString(3, "%" + word + "%");
			lPreparedStatementContact.setString(4, "%" + word + "%");
			ResultSet rsContact = lPreparedStatementContact.executeQuery();

			while (rsContact.next()) {

				final int id = rsContact.getInt("ID");
				final String lastName = rsContact.getString("LASTNAME");
				final String firstName = rsContact.getString("FIRSTNAME");
				final String email = rsContact.getString("EMAIL");

				contacts.add(new Contact(id, lastName, firstName, email, null, null, null));
			}

			display.setContacts(contacts);

		} catch (NamingException e) {

			System.out.println(e.getMessage());
			display.setError("NamingException : " + e.getMessage());

		} catch (SQLException e) {

			System.out.println(e.getMessage());
			display.setError("SQLException : " + e.getMessage());

		} finally {
			try {
				if (lConnection != null)
					lConnection.close();
			} catch (SQLException e) {
				display.setError("Erreur : " + e.getMessage());
			}
		}
		return display;
	}

	public List<Address> searchAddress(String word) {

		System.out.println("Entre dans search address DAO");

		Connection lConnection = null;
		final List<Address> address = new LinkedList<Address>();

		try {
			final Context lContext = new InitialContext();
			final DataSource lDataSource = (DataSource) lContext.lookup(RESOURCE_JDBC);
			lConnection = lDataSource.getConnection();

			word = word.replace("!", "!!").replace("%", "!%").replace("_", "!_").replace("[", "![");

			final PreparedStatement lPreparedStatementAddress =

					lConnection.prepareStatement(
							"SELECT ID, STREET, CITY, ZIP, COUNTRY FROM address WHERE ID LIKE ? OR STREET LIKE ? OR CITY LIKE ? OR ZIP LIKE ? OR COUNTRY LIKE ?");

			lPreparedStatementAddress.setString(1, "%" + word + "%");
			lPreparedStatementAddress.setString(2, "%" + word + "%");
			lPreparedStatementAddress.setString(3, "%" + word + "%");
			lPreparedStatementAddress.setString(4, "%" + word + "%");
			lPreparedStatementAddress.setString(5, "%" + word + "%");
			ResultSet rsAddress = lPreparedStatementAddress.executeQuery();

			while (rsAddress.next()) {

				final int id = rsAddress.getInt("ID");
				final String street = rsAddress.getString("STREET");
				final String city = rsAddress.getString("CITY");
				final String zip = rsAddress.getString("ZIP");
				final String country = rsAddress.getString("COUNTRY");

				address.add(new Address(id, street, city, zip, country));
			}

		} catch (NamingException e) {

			System.out.println(e.getMessage());
			address.add(new Address(0, "NamingException : " + e.getMessage(), null, null, null));

		} catch (SQLException e) {

			System.out.println(e.getMessage());
			address.add(new Address(0, "SQLException : " + e.getMessage(), null, null, null));

		} finally {
			try {
				if (lConnection != null)
					lConnection.close();
			} catch (SQLException e) {
				address.add(new Address(0, "Erreur : " + e.getMessage(), null, null, null));
			}
		}
		return address;
	}
}
