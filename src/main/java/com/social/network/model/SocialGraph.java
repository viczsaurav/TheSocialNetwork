package com.social.network.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * The class represents the Social Graph representation of all users using Adjacency List
 */
public class SocialGraph {

	private static final Set<GraphNode> socialGraph = new HashSet<>();

	public static void updateSocialGraph(Person sender, Set<Person> recipientList){
		GraphNode senderNode = new GraphNode(sender);
		socialGraph.add(senderNode);
		for (Person receiver: recipientList) {
			System.out.println("Sender: "+sender.getName()+ ", receiver: "+ receiver.getEmail());
			GraphNode recipientNode = new GraphNode(receiver);
			recipientNode.connect(senderNode);
			socialGraph.add(recipientNode);
			System.out.println("---------------");
		};
	}

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
}
