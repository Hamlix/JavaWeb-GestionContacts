package org.lip6.struts.domain;

import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

public class DAOAdd {

    public void addContact( final String firstName, final String lastName, final String email){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        ContactEntity contact = new ContactEntity();
        contact.setFirstName(firstName);
        contact.setLastName(lastName);
        contact.setEmail(email);

        Transaction transaction = session.getTransaction();
        session.persist(contact);
        transaction.commit();
        session.close();
    }

    public void addAddress(final ContactEntity contact, final String street, final String city, final String zip, final String country){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        AddressEntity address = new AddressEntity();
        address.setStreet(street);
        address.setCity(city);
        address.setZip(zip);
        address.setCountry(country);
        address.setContact(contact);
        contact.addAddress(address);

        Transaction transaction = session.getTransaction();
        session.persist(address);
        session.persist(contact);
        transaction.commit();
        session.close();
    }

    public void addPhone(final ContactEntity contact, final String phoneKind, final String phoneNumber){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        PhonenumberEntity pn = new PhonenumberEntity();
        pn.setPhoneKind(phoneKind);
        pn.setPhoneNumber(phoneNumber);
        contact.addPhonenumber(pn);

        Transaction transaction = session.getTransaction();
        session.persist(pn);
        session.persist(contact);
        transaction.commit();
        session.close();
    }

    public void addGroup(final ContactEntity contact, final String groupName){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        ContactgroupEntity cg = new ContactgroupEntity();
        cg.setGroupName(groupName);
        cg.setContact(contact);
        contact.addGroups(cg);

        Transaction transaction = session.getTransaction();
        session.persist(cg);
        session.persist(contact);
        transaction.commit();
        session.close();
    }

    public void addContactToGroup(final ContactEntity contact, final ContactgroupEntity group){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        group.addContact(contact);

        Transaction transaction = session.getTransaction();
        session.persist(group);
        transaction.commit();
        session.close();
    }


}
