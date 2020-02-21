package com.social.network.main;

import com.social.network.model.GraphNode;
import com.social.network.model.SocialGraph;
import com.social.network.utils.EMLParser;

import java.io.*;
import java.util.*;

/**
 * Main Control method to Start the reading of .eml files and populating SocialGraph
 */
public class ApplicationMain {

	//For Reader
	private static String downloadedFilePath;
	private static String downloadedFileName;
	private static String blacklistFilename;

	// For Writer
	private static String outputEdgeListFilename;

	private static ClassLoader classLoader;

	public static void main(String[] args) throws Exception{
		ApplicationMain main = new ApplicationMain();
		classLoader = main.getClass().getClassLoader();
		Map<String, String> configMap  = main.initProperties();

		downloadedFilePath = configMap.get("downloaded.filePath");
		downloadedFileName = configMap.get("downloaded.fileName");
		blacklistFilename = configMap.get("blacklist.filename");
		outputEdgeListFilename = configMap.get("output.edge-list.filename");

		if (downloadedFilePath==null || downloadedFileName==null ||
						blacklistFilename==null || outputEdgeListFilename==null){
			throw new Exception("Please check the config file.. inputs missing..");
		}

		main.setup(configMap);

		System.out.println("Size of Network : "+ SocialGraph.getSocialNetworkSize());
		main.writeEdgesToFile(SocialGraph.getSocialGraph());
	}

	private Map<String, String> initProperties() throws Exception{
		Map<String, String> configMap = new HashMap<>();
		try (InputStream input = classLoader.getResourceAsStream("config.properties")) {

			Properties prop = new Properties();

			if (input == null) {
				System.out.println("Sorry, unable to find config.properties");
				throw new Exception("No config file found");
			}

			//load a properties file from class path, inside static method
			prop.load(input);
			prop.forEach((k,v)-> configMap.put(k.toString(),v.toString()));

		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return configMap;
	}

	private void setup(Map<String, String> configMap) throws Exception{
		// TODO Think about threads
		Set<String> blackList = this.getFileList(blacklistFilename);
		Set<String> fileList = this.getFileList(downloadedFileName);

		fileList.stream().forEach(path -> {
			try{
				// Skip checksum failed file
				if (!blackList.contains(path)){
					// Read email and update social graph
					EMLParser emlParse = new EMLParser(path);
					SocialGraph.updateSocialGraph(emlParse.getSender(), emlParse.getRecipientList());
				}
			}
			catch (Exception e){
				System.out.println("\nFile Failed: "+ path + "\n Error: " + e.getMessage());
			}
		});
	}

	private Set<String> getFileList(String filename) throws IOException {
		Set<String> fileList =  new HashSet<>();
		File file = new File(classLoader.getResource(filename).getFile());

		try (FileReader reader = new FileReader(file);
				 BufferedReader br = new BufferedReader(reader)) {
					String fileName;
					while ((fileName = br.readLine()) != null) {
						fileList.add(downloadedFilePath+fileName);
			}
		}

		return fileList;
	}

	private void writeEdgesToFile(Set<GraphNode> socialGraph) throws IOException {

		FileOutputStream outputStream = new FileOutputStream(outputEdgeListFilename);
		socialGraph.stream().forEach(node -> {
				String sender = node.getValue().getEmail();
				node.getNeighbors()
						.forEach(receiver ->{
									String edge = sender + "," +receiver.getValue().getEmail() + "\n";
									byte[] strToBytes = edge.getBytes();
									try{
										outputStream.write(strToBytes);
									}
									catch (Exception e){
										System.out.println("\nFailed for edge: ["+ edge + "]\n Error: " + e.getMessage());
									}
				});
		});
		outputStream.close();

	}
}
