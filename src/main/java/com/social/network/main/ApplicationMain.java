package com.social.network.main;

import com.social.network.model.SocialGraph;
import com.social.network.utils.EMLParser;

import java.util.HashSet;
import java.util.Set;

/**
 * Main Control method to Start the reading of .eml files and populating SocialGraph
 */
public class ApplicationMain {

	public static void main(String[] args) {
		String path= "/Users/sverma/Projects/personal/TheSocialNetwork/src/main/resources/sample.eml";
		setup();
		SocialGraph.getSocialGraph()
						.stream().forEach(p ->
						System.out.println(p.getValue().getName()+","+ p.getNeighbors().size()));

		System.out.println(SocialGraph.getSocialNetworkSize());
	}

	private static void setup() {
		// TODO Think about threads
		Set<String> fileList = getFileList();
		fileList.forEach(path -> {
			try{
				EMLParser emlParse = new EMLParser(path);
				// After processing each email, the SocialGraph must be updated
				SocialGraph.updateSocialGraph(emlParse.getSender(), emlParse.getRecipientList());
			}
			catch (Exception e){
				System.out.println("\nFile Failed: "+ path + "\n Error: " + e.getMessage());
			}
		});
	}

	private static Set<String> getFileList(){
		String path= "/Users/sverma/Documents/saurav-verma/data/downloaded-1/_files_allen-p__sent_mail_100061657796e7237e0f4aa2c25135ece310";

		Set<String> fileList =  new HashSet<>();
		fileList.add(path);
		return fileList;
	}
}
