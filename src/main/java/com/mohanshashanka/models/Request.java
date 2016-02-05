package com.mohanshashanka.models;

import com.mohanshashanka.exceptions.UnAuthorizedException;

public class Request {
	// Access Coding
	private Role accessRole;
	private Object[] requestObjects;

	public Request() {

	}
	
	public Request(Role accessRole, Object[] requestObjects) {
		this.accessRole = accessRole;
		this.requestObjects = requestObjects;
	}

	public Object[] getRequestObjects() {
		return requestObjects;
	}

	public void setRequestObjects(Object[] requestObjects) {
		this.requestObjects = requestObjects;
	}
	
	public Role getAccessRolee() {
		return accessRole;
	}

	public void setAccessRole(Role accessRole) {
		this.accessRole = accessRole;
	}

	public boolean hasAdminAccess() throws UnAuthorizedException {
		if(Role.ADMIN.equals(this.accessRole)){
			return true;
		} else {
			throw new UnAuthorizedException("Un-Authorised to perform the action");
		}
	}
}
