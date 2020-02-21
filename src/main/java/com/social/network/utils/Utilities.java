package com.social.network.utils;

import com.social.network.model.GraphNode;

import java.io.*;
import java.util.*;

public class Utilities {

	public static Map<String, String> initProperties(ClassLoader classLoader) throws Exception{
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

	public static Set<String> getFileList(File file, String downloadedFilePath) throws IOException {
		Set<String> fileList =  new HashSet<>();

		try (FileReader reader = new FileReader(file);
				 BufferedReader br = new BufferedReader(reader)) {
			String fileName;
			while ((fileName = br.readLine()) != null) {
				fileList.add(downloadedFilePath+fileName);
			}
		}
		return fileList;
	}


	public static void writeEdgesToFile(Set<GraphNode> socialGraph, String outputEdgeListFilename) throws IOException {

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
