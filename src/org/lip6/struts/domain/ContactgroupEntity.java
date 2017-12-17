package org.lip6.struts.domain;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "contactgroup", schema = "hibernateproject")
public class ContactgroupEntity {
    private int idGroup;
    private String groupName;
    private ContactEntity contact;
    private Collection<ContactEntity> contacts;

    @Id
    @Column(name = "IdGroup")
    public int getIdGroup() {
        return idGroup;
    }

    public void setIdGroup(int idGroup) {
        this.idGroup = idGroup;
    }

    @Basic
    @Column(name = "GroupName")
    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @ManyToOne()
    public ContactEntity getContact()
    { return contact; }
    public void setContact(ContactEntity c)
    { this.contact = c; }

    @ManyToMany
    public Collection<ContactEntity> getContacts() {
        return contacts;
    }
    public void setContacts(Collection<ContactEntity> contacts) {
        this.contacts = contacts;
    }
    public void addContact(ContactEntity c){
        this.contacts.add(c);
    }
    public void removeContact(ContactEntity c){
        this.contacts.remove(c);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContactgroupEntity that = (ContactgroupEntity) o;
        return idGroup == that.idGroup &&
                Objects.equals(groupName, that.groupName);
    }

    @Override
    public int hashCode() {

        return Objects.hash(idGroup, groupName);
    }
}
