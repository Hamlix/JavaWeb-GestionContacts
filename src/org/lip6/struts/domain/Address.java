package org.lip6.struts.domain;

public class Address {

	private int id;
	private int idContact;
	private String street;
	private String city;
	private String zip;
	private String country;

	public Address(){

	}
	
	public Address(int id, String street, String city, String zip, String country) {
		this.id = id;
		this.street = street;
		this.city = city;
		this.zip = zip;
		this.country = country;
	}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdContact() {
        return idContact;
    }

    public void setIdContact(int idContact) {
        this.idContact = idContact;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
	public String toString() {
		return "Address [id=" + id + ", street=" + street + ", city=" + city + ", zip=" + zip + ", country=" + country
				+ "]";
	}
}
