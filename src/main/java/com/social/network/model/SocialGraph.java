package com.social.network.model;

import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The class represents the Social Graph representation of all users using Adjacency List
 */
public class SocialGraph {

	private static final Logger logger = LoggerFactory.getLogger(SocialGraph.class);
	private static final Map<Person, Set<Person>> socialGraph = new HashMap<>();

	public static void updateSocialGraph(Person sender, Set<Person> recipientList){
		logger.debug("--- updateSocialGraph ----- : "+ sender.getName());
		Set<Person> existingSet = socialGraph.get(sender);
		if(existingSet!=null){
			existingSet.addAll(recipientList);
		} else {
			existingSet = recipientList;
		}
		socialGraph.put(sender, existingSet);
	}

	public static Map<Person, Set<Person>> getSocialGraph(){
		return Collections.unmodifiableMap(socialGraph);
	}

	public static Set<Person> getNeighbours(Person person){
		logger.debug("--- getNeighbours ---- : "+ person.getName());
		Set<Person> existingSet = socialGraph.get(person);
		return Collections.unmodifiableSet(existingSet==null?new HashSet<>(): existingSet);
	}
}
