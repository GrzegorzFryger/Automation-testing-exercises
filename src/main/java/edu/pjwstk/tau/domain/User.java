package edu.pjwstk.tau.domain;

import lombok.Builder;

import java.util.Objects;

@Builder
public class User implements  Cloneable, DeepClone<User>{
	private int id;
	private String name;
	private String surname;
	private String password;
	private String email;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() { return name; }

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof User)) return false;

		User user = (User) o;

		if (getId() != user.getId()) return false;
		if (getName() != null ? !getName().equals(user.getName()) : user.getName() != null) return false;
		if (getSurname() != null ? !getSurname().equals(user.getSurname()) : user.getSurname() != null) return false;
		if (getPassword() != null ? !getPassword().equals(user.getPassword()) : user.getPassword() != null)
			return false;
		return getEmail() != null ? getEmail().equals(user.getEmail()) : user.getEmail() == null;
	}

	@Override
	public int hashCode() {
		int result = getId();
		result = 31 * result + (getName() != null ? getName().hashCode() : 0);
		result = 31 * result + (getSurname() != null ? getSurname().hashCode() : 0);
		result = 31 * result + (getPassword() != null ? getPassword().hashCode() : 0);
		result = 31 * result + (getEmail() != null ? getEmail().hashCode() : 0);
		return result;
	}

	@Override
	protected User clone() throws CloneNotSupportedException {
		return (User) super.clone();
	}

	@Override
	public User deepClone() {
		User user;
		try {
			user =  this.clone();
		} catch (CloneNotSupportedException e) {
			return null;
		}
		return user;
	}
}
