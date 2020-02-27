package com.social.network.model;

import java.util.*;

/**
 * This Person class represents a Node in the Social Network graph.
 * Each person has email. This can be extended to include name and secondary email property.
 */
public class Person {

	private String name;

	private Set<String> emails = new HashSet<>();

	public Person(String name, String email){
		this.name = name;
		this.emails.add(email);
	}

	public String getName(){
		return this.name;
	}
	public Set<String> getEmails() {
		return Collections.unmodifiableSet(emails);
	}
  
	public void mergeEmails(Person node){
		this.emails.addAll(node.getEmails());
		node.emails.addAll(this.getEmails());
	}

	/**
	 * Create List of person objects when Name and email list provided in same order
	 * @param emails
	 * @return
	 */
	public static Set<Person> getPersonList(List<String> names, List<String> emails) {
		List<Person> listOfPersons= new ArrayList<>();

		if(names.size()!= emails.size()){
			throw new IllegalArgumentException("Mismatch in size of Sets.. " +
							"namesArray=["+names.size()+"], emailsArray=["+emails.size()+"]");
		}

		for(int i=0; i< names.size();i++){
			listOfPersons.add(new Person(names.get(i), emails.get(i)));
		}
		return new HashSet<>(listOfPersons);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Person person = (Person) o;
		return name.equals(person.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name);
	}
}
