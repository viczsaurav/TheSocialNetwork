package com.social.network.model;

import java.util.*;

/**
 * The class represents the Social Graph representation of all users using Adjacency List
 */
public class SocialGraph {

	private static final Map<GraphNode, GraphNode> socialGraph = new HashMap<>();
//	private static final Set<GraphNode> socialGraph = new HashSet<>();

	public static void updateSocialGraph(Person sender, Set<Person> recipientList){
		//Process sender
		GraphNode senderNode = new GraphNode(sender);
		if (socialGraph.containsKey(senderNode)){
			senderNode.merge(socialGraph.get(senderNode));
		}
		socialGraph.put(senderNode, senderNode);

		//Process recipientList
		for (Person receiver: recipientList) {
			GraphNode recipientNode = new GraphNode(receiver);
			recipientNode.connect(senderNode);
			socialGraph.put(recipientNode, recipientNode);
		};
	}

	/**
	 * Return Copy of the socialGraph
	 * @return
	 */
	public static Set<GraphNode> getSocialGraph(){
		return Collections.unmodifiableSet(socialGraph.keySet());
	}

	/**
	 * Return size of the network
	 * @return
	 */
	public static int getSocialNetworkSize(){
		return socialGraph.size();
	}
}
