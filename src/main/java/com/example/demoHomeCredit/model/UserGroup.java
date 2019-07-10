package com.example.demoHomeCredit.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "user_group")
public class UserGroup implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "gen", sequenceName = "user_group_id_seq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen")
	@Column(name = "user_group_id")
	long userGroupId;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	Users users;
	
	@ManyToOne
	@JoinColumn(name = "menu_id")
	Lookup menu;
	
	@Column(name="view_seq")
	long viewSeq;

	public long getUserGroupId() {
		return userGroupId;
	}

	public void setUserGroupId(long userGroupId) {
		this.userGroupId = userGroupId;
	}

	public Users getUsers() {
		return users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

	public Lookup getMenu() {
		return menu;
	}

	public void setMenu(Lookup menu) {
		this.menu = menu;
	}

	public long getViewSeq() {
		return viewSeq;
	}

	public void setViewSeq(long viewSeq) {
		this.viewSeq = viewSeq;
	}

		

}
