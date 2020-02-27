package com.social.network.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * The class represents the Social Graph representation of all users using Adjacency List
 */
public class SocialGraph {

	private static final Map<GraphNode, GraphNode> socialGraph = new HashMap<>();

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
			if (socialGraph.containsKey(recipientNode)){
				recipientNode.merge(socialGraph.get(recipientNode));
			}
			recipientNode.connect(senderNode);
			socialGraph.put(recipientNode, recipientNode);
		}
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
