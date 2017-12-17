package org.lip6.struts.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "contactgroup", schema = "hibernateproject", catalog = "")
public class ContactgroupEntity {
    private int idGroup;
    private String groupName;

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
