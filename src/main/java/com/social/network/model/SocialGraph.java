package com.social.network.model;

import com.social.network.utils.Utilities;

import java.util.*;

/**
 * The class represents the Social Graph representation of all users using Adjacency List
 */
public class SocialGraph {

	private static final Set<GraphNode> socialGraph = new HashSet<>();

	/**
	 * Return Copy of the socialGraph
	 * @return
	 */
	public static Set<GraphNode> getSocialGraph(){
		return Collections.unmodifiableSet(socialGraph);
	}

	/**
	 * Return size of the network
	 * @return
	 */
	public static int getSocialNetworkSize(){
		return socialGraph.size();
	}

	/**
	 * Updating Social Graph for *NEW* Senders and recipients
	 * @param sender
	 * @param recipientList
	 */
	public static void updateSocialGraph(Person sender, Set<Person> recipientList){
		//Process sender
		GraphNode newSenderNode = getGraphNode(sender);

		//Process recipientList
		for (Person receiver: recipientList) {
			GraphNode newRecipientNode = getGraphNode(receiver);
			newRecipientNode.connect(newSenderNode);
		}
	}

	/**
	 * Find closest node match and merge
	 * @param person
	 * @return
	 */
	private static GraphNode getGraphNode(Person person) {
		GraphNode newNode = new GraphNode(person);
		GraphNode closestNode = Utilities.closestMatch(newNode, socialGraph);

		if (closestNode != null) {
			closestNode.mergeContact(newNode);
			newNode = closestNode;
		} else {
			socialGraph.add(newNode);
		}
		return newNode;
	}

//	public static void main(String[] args) {
//		Set<Person> recipientList = new HashSet<>();
//
//		Person p1 =  new Person("phillip.allen@enron.com");
//		Person p2 =  new Person("pallen@enron.com");
//		Person p3 =  new Person("viczsaurav@enron.com");
//
//		recipientList.add(p2);
//		recipientList.add(p3);
//
//		updateSocialGraph(p1, recipientList);
//
//		socialGraph.stream().forEach(node -> {
//			String sender = node.getValue().getPrimaryEmail();
//			node.getNeighbors()
//							.forEach(receiver -> {
//								String edge = sender + " -> " + receiver.getValue().getPrimaryEmail()+ "\n";
//								System.out.println(edge);
//								System.out.println("----------");
//								receiver.getValue().getEmails().forEach(System.out::println);
//								System.out.println("----------");
//								System.out.println("================");
//
//							});
//		});
//	}

}
