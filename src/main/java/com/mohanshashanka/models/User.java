package com.mohanshashanka.models;

public class User {
	private int uId;
	private String name;
	private String department;
	private Role role;
	private String userName;
	// Password not retrieved for security

	public int getuId() {
		return uId;
	}

	public void setuId(int uId) {
		this.uId = uId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public Role getRole() {
		return role;
	}
	
	public String getRoleAsString() {
		return role != null ? role.name() : role.USER.name();
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public void setRole(String role) {
		if (Role.ADMIN.name().equalsIgnoreCase(role)) {
			this.role = Role.ADMIN;
		} else if (Role.USER.name().equalsIgnoreCase(role)) {
			this.role = Role.USER;
		}
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	// METHODS

	// **** methods for checking is the user is an admin / not ****

	public boolean isAdmin() {
		if (Role.ADMIN.equals(role)){
			return true;
		} else {
			return false;
		}
	}
	
}
