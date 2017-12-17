package org.lip6.struts.domain;

public class PhoneNumber {

	private int id;
	private int idContact;
	private String phoneKind;
	private String phoneNumber;
	private String errors;

	public PhoneNumber(){

	}
	
	public PhoneNumber(int id, int idContact, String phoneKind, String phoneNumber, String errors){
		this.id = id;
		this.idContact = idContact;
		this.phoneKind = phoneKind;
		this.phoneNumber = phoneNumber;
		this.errors = errors;
	}

	/**
	 * @return ID Returns ID
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * @return ID Returns ID
	 */
	public int getIdContact() {
		return idContact;
	}

	/**
	 * @return Phone Kind
	 */
	public String getPhoneKind() {
		return phoneKind;
	}

	/**
	 * @return Phone Number
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	/**
	 * @return Errors
	 */
	public String getErrors() {
		return errors;
	}

	/**
	 * @param l
	 *            Sets the ID
	 */
	public void setId(int l) {
		id = l;
	}
	
	/**
	 * @param l
	 *            Sets the ID
	 */
	public void setIdContact(int l) {
		idContact = l;
	}

	/**
	 * @param string
	 *            sets the Phone Kind
	 */
	public void setPhoneKind(String string) {
		phoneKind = string;
	}

	/**
	 * @param string
	 *            sets the Phone Number
	 */
	public void setPhoneNumber(String string) {
		phoneNumber = string;
	}

	@Override
	public String toString() {
		return "PhoneNumber [id=" + id + ", idContact=" + idContact + ", phoneKind=" + phoneKind + ", phoneNumber="
				+ phoneNumber + "]";
	}
}
