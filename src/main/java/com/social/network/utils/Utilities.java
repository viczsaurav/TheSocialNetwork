package com.social.network.utils;

import com.social.network.model.GraphNode;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

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
			String sender = (String) node.getValue().getEmails().toArray()[0];
			node.getNeighbors()
							.forEach(receiver ->{
								String edge = sender + "," + receiver.getValue().getEmails().toArray()[0]+ "\n";
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

	public static void writeToFile(String filename, String writePath) throws Exception{
		try(FileOutputStream fos = new FileOutputStream(writePath, true);){
			fos.write((filename+"\n").getBytes());
		}
		catch(Exception e){
			System.out.println("Error writing to failed-files");
		}
	}
	public static boolean isValidEmail(String email) {
		String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
		return email.matches(regex);
	}

	public static String listToString(Set<String> input, String delimiter){
		return input.stream().collect(Collectors.joining(delimiter));
	}
}
