package com.social.network.model;

import java.util.*;

/**
 * This Person class represents a Node in the Social Network graph.
 * Each person has email. This can be extended to include name and secondary email property.
 */
public class Person {

	private String primaryEmail;
	private Set<String> emails = new HashSet<>();

	public Person(String email){
		this.primaryEmail=email;
		this.emails.add(email);
	}

	public String getPrimaryEmail(){
		return this.primaryEmail;
	}

	public Set<String> getEmails() {
		return Collections.unmodifiableSet(emails);
	}
  
	public void mergeEmails(Person newNode){
		this.emails.addAll(newNode.getEmails());
	}


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Person person = (Person) o;
		return primaryEmail.equals(person.primaryEmail);
	}

	@Override
	public int hashCode() {
		return Objects.hash(primaryEmail);
	}
}
