package com.social.network.main;

import com.social.network.model.SocialGraph;
import com.social.network.utils.EMLParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Set;

/**
 * Main Control method to Start the reading of .eml files and populating SocialGraph
 */
public class ApplicationMain {

	public static void main(String[] args) throws Exception{
		ApplicationMain main = new ApplicationMain();
		main.setup();
		SocialGraph.getSocialGraph()
						.stream().forEach(p ->
						System.out.println(p.getValue().getName()+","+ p.getNeighbors().size()));

		System.out.println(SocialGraph.getSocialNetworkSize());
	}

	private void setup() throws Exception{
		// TODO Think about threads
		Set<String> fileList = this.getFileList();
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

	private Set<String> getFileList() throws Exception {
		String basePath = "/Users/sverma/Documents/saurav-verma/data/downloaded-1/";
		String downloadedFileNameList ="downloaded-1.txt";
		Set<String> fileList =  new HashSet<>();

		File file = new File(
						getClass().getClassLoader().getResource(downloadedFileNameList).getFile()
		);

		try (FileReader reader = new FileReader(file);
				 BufferedReader br = new BufferedReader(reader)) {
					String fileName;
					while ((fileName = br.readLine()) != null) {
						fileList.add(basePath+fileName);
			}
		}
		return fileList;
	}
}
