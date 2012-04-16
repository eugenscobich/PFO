package ru.pfo.model.security;

public enum AuthorityType {

	ROLE_USER, ROLE_ADMIN;

	private AuthorityType() {
	}

	public String getName() {
		return this.name();
	}

}
