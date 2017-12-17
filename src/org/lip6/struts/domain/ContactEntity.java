package org.lip6.struts.domain;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "contact", schema = "hibernateproject")
public class ContactEntity {
    private int idContact;
    private String firstName;
    private String lastName;
    private String email;
    private String errorContact;
    private Collection<AddressEntity> addresses;
    private Collection<ContactgroupEntity> contactgroups;
    private Collection<PhonenumberEntity> phonenumbers;
    private Collection<ContactgroupEntity> groups;

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

    @OneToMany(mappedBy="contact", cascade = "CascadeType.REMOVE" )
    public Collection<AddressEntity> getAddresses()
    { return addresses; }
    public void setAddresses (Collection<AddressEntity> adr)
    { this.addresses = adr; }
    public void addAddress (AddressEntity adr){
        this.addresses.add(adr);
    }
    public void removeAddress(AddressEntity adr){
        this.addresses.remove(adr);
    }

    @OneToMany(mappedBy="contact", cascade="CascadeType.REMOVE")
    public Collection<ContactgroupEntity> getContactgroups()
    { return contactgroups; }
    public void setContactgroups (Collection<ContactgroupEntity> cg)
    { this.contactgroups = cg; }
    public void addContactgoup (ContactgroupEntity cg){
        this.contactgroups.add(cg);
    }
    public void removeContactgroup (ContactgroupEntity cg){
        this.contactgroups.remove(cg);
    }

    @OneToMany(mappedBy="contact",cascade="CascadeType.REMOVE")
    public Collection<PhonenumberEntity> getPhonenumbers()
    { return phonenumbers; }
    public void setPhonenumbers (Collection<PhonenumberEntity> ph)
    { this.phonenumbers = ph; }
    public void addPhonenumber (PhonenumberEntity ph){
        this.phonenumbers.add(ph);
    }
    public void removePhonenumber (PhonenumberEntity ph){
        this.phonenumbers.remove(ph);
    }

    @ManyToMany(mappedBy = "contacts")
    public Collection<ContactgroupEntity> getGroups() {
        return groups;
    }
    public void setGroups(Collection<ContactgroupEntity> groups) {
        this.groups = groups;
    }
    public void addGroups(ContactgroupEntity g){
        this.groups.add(g);
    }
    public void removeGroups(ContactgroupEntity g){
        this.groups.remove(g);
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
