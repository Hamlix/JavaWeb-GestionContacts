package org.lip6.struts.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "phonenumber", schema = "hibernateproject", catalog = "")
public class PhonenumberEntity {
    private int idPhoneNumber;
    private String phoneKind;
    private String phoneNumber;
    private String errorPhoneNumber;

    @Id
    @Column(name = "IdPhoneNumber")
    public int getIdPhoneNumber() {
        return idPhoneNumber;
    }

    public void setIdPhoneNumber(int idPhoneNumber) {
        this.idPhoneNumber = idPhoneNumber;
    }

    @Basic
    @Column(name = "PhoneKind")
    public String getPhoneKind() {
        return phoneKind;
    }

    public void setPhoneKind(String phoneKind) {
        this.phoneKind = phoneKind;
    }

    @Basic
    @Column(name = "PhoneNumber")
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Basic
    @Column(name = "ErrorPhoneNumber")
    public String getErrorPhoneNumber() {
        return errorPhoneNumber;
    }

    public void setErrorPhoneNumber(String errorPhoneNumber) {
        this.errorPhoneNumber = errorPhoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PhonenumberEntity that = (PhonenumberEntity) o;
        return idPhoneNumber == that.idPhoneNumber &&
                Objects.equals(phoneKind, that.phoneKind) &&
                Objects.equals(phoneNumber, that.phoneNumber) &&
                Objects.equals(errorPhoneNumber, that.errorPhoneNumber);
    }

    @Override
    public int hashCode() {

        return Objects.hash(idPhoneNumber, phoneKind, phoneNumber, errorPhoneNumber);
    }
}
