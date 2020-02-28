package com.social.network.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class GraphNode {
	private Person value;
	private Set<GraphNode> neighbors;

	public GraphNode(Person value) {
		this.value = value;
		this.neighbors = new HashSet<>();
	}

	public Person getValue() {
		return this.value;
	}

	public Set<GraphNode> getNeighbors() {
		return this.neighbors;
	}


	/**
	 * Connecting edges
	 * @param node
	 */
	public void connect(GraphNode node) {
		if (!this.equals(node)) {
			this.neighbors.add(node);
			node.neighbors.add(this);		// Since its undirected graph
		}
	}

	/**
	 * Merge Nodes if the next email is not present in existing node
	 * @param newNode
	 */
	public void mergeContact(GraphNode newNode) {
		this.value.mergeEmails(newNode.value);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		GraphNode graphNode = (GraphNode) o;
		return value.equals(graphNode.value);
	}

	@Override
	public int hashCode() {
		return Objects.hash(value);
	}


//	public static void main(String[] args) {
//		Person p1 =  new Person("viczsaurav@gmail.com");
//		GraphNode n1 = new GraphNode(p1);
//
//		Person p2 =  new Person("saurav@gmail.com");
//		GraphNode n2 = new GraphNode(p2);
//
//		n1.mergeContact(n2);
//
//		System.out.println("p1 primary: "+ p1.getPrimaryEmail());
//		System.out.println("p2 primary: "+ p2.getPrimaryEmail());
//
//		p1.getEmails().forEach(System.out::println);
//		System.out.println("------------");
//		p2.getEmails().forEach(System.out::println);
//	}
}


