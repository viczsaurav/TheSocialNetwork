package com.social.network.model;

import java.util.Objects;

/**
 * This Person class represents a Node in the Social Network graph.
 * Each person has email. This can be extended to include name and secondary email property.
 */
public class Person {

	private String email;

	public Person(String email){
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Person person = (Person) o;
		return email.equals(person.email);
	}

	@Override
	public int hashCode() {
		return Objects.hash(email);
	}
}
