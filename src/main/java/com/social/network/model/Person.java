package com.social.network.model;

import java.util.*;

/**
 * This Person class represents a Node in the Social Network graph.
 * Each person has name, email properties and connections(neighbours)
 */
public class Person {

	private String name;
	private String email;

	private Set<Person> neighbors;

	public Person(String name, String email){
		this.name = name;
		this.email = email;
		this.neighbors = new HashSet<Person>();
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public Set<Person> getNeighbors() {
		return Collections.unmodifiableSet(neighbors);
	}

	/**
	 * Create List of person objects when Name and email list provided in same order
	 * @param names
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
}
