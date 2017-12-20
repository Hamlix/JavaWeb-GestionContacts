package org.lip6.struts.domain;

public class ContactGroup {

	private int groupId;
	private String groupName;
	private int idContact;

    public ContactGroup() {

    }

	public ContactGroup(int groupId, String groupName) {
		this.groupId = groupId;
		this.groupName = groupName;
		this.idContact = idContact;
	}
	public ContactGroup(int groupId, String groupName, int idContact) {
		this.groupId = groupId;
		this.groupName = groupName;
		this.idContact = idContact;
	}


	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public int getIdContact() {
		return idContact;
	}

	public void setIdContact(int idContact) {
		this.idContact = idContact;
	}

	/**
	 * @return GroupName
	 */
	public String getGroupName() {
		return groupName;
	}


	/**
	 * @param string
	 *            sets the Group Name
	 */
	public void setGroupName(String string) {
		groupName = string;
	}

	@Override
	public String toString() {
		return "ContactGroup [groupId=" + groupId + ", groupName=" + groupName + "]";
	}
}
