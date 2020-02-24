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
	 * Merge Nodes if
	 * @param node
	 */
	public void merge(GraphNode node) {
		Set<String> emails = node.value.getEmails();
		if (this.equals(node) &&
						!this.value.getEmails().stream().anyMatch(emails::contains)){
				this.value.mergeEmails(emails);
		}
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
}


