package com.social.network.main;

import com.social.network.model.SocialGraph;
import com.social.network.utils.EMLParser;

/**
 * Main Control method to Start the reading of .eml files and populating SocialGraph
 */
public class ApplicationMain {

	public static void main(String[] args) throws Exception {
		String path= "/Users/sverma/Projects/personal/TheSocialNetwork/src/main/resources/sample.eml";
		EMLParser e = new EMLParser(path);

		SocialGraph.getSocialGraph().keySet()
						.stream().forEach(p ->
						System.out.println(p.getName()+","+ p.getEmail()));

		SocialGraph.getNeighbours(e.getSender())
						.stream().forEach(p ->
						System.out.println(p.getName()+","+ p.getEmail()));
	}
}
