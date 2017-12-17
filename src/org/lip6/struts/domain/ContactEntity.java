package org.lip6.struts.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "contact", schema = "hibernateproject")
public class ContactEntity {
    private int idContact;
    private String firstName;
    private String lastName;
    private String email;
    private String errorContact;

    @Id
    @Column(name = "IdContact")
    public int getIdContact() {
        return idContact;
    }

    public void setIdContact(int idContact) {
        this.idContact = idContact;
    }

    @Basic
    @Column(name = "FirstName")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Basic
    @Column(name = "LastName")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Basic
    @Column(name = "Email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "ErrorContact")
    public String getErrorContact() {
        return errorContact;
    }

    public void setErrorContact(String errorContact) {
        this.errorContact = errorContact;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContactEntity that = (ContactEntity) o;
        return idContact == that.idContact &&
                Objects.equals(firstName, that.firstName) &&
                Objects.equals(lastName, that.lastName) &&
                Objects.equals(email, that.email) &&
                Objects.equals(errorContact, that.errorContact);
    }

    @Override
    public int hashCode() {

        return Objects.hash(idContact, firstName, lastName, email, errorContact);
    }
}
