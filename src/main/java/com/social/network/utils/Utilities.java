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
			String sender = node.getValue().getPrimaryEmail();
			node.getNeighbors()
							.forEach(receiver ->{
								String edge = sender + "," + receiver.getValue().getPrimaryEmail()+ "\n";
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

	/**
	 *
	 * Levenshtein Edit Distance matrix
	 *
	 * 	   ""	O R I G S T R
	 * 	"" 0  1 2 3 4 5 6 7
	 * 	F  1
	 * 	I  2
	 * 	N  3
	 * 	A  4
	 * 	L  5
	 * 	S  6
	 * 	T  7
	 * 	R  8
	 *
	 * @param origStr
	 * @param finalStr
	 * @return
	 */
	public static int findMinEditDistance(String origStr, String finalStr){
		int [][] editMatrix = new int[origStr.length()+1][finalStr.length()+1];


		for (int i=0;i<editMatrix.length;i++){				// editMatrix.length gives to length x-axis in editMatrix[X][Y]
			editMatrix[i][0] = i;
		}
		for (int j=0;j<editMatrix[0].length;j++){			// editMatrix[0].length gives to length y-axis in editMatrix[X][Y]
			editMatrix[0][j] = j;
		}

		for (int j=1;j<editMatrix[0].length;j++){
			for(int i=1;i<editMatrix.length;i++){

				// When char[i] = char[j]
				if (origStr.charAt(i-1)==finalStr.charAt(j-1)){
					editMatrix[i][j] = editMatrix[i-1][j-1];
				}
				else {
					editMatrix[i][j] =  Math.min(editMatrix[i][j-1],
									Math.min(editMatrix[i-1][j-1], editMatrix[i-1][j])) + 1;
				}
			}
		}
		return editMatrix[editMatrix.length-1][editMatrix[0].length-1];
	}

	public static GraphNode closestMatch(GraphNode originalNode, Set<GraphNode> graphSet){
		final int matchValueHeuristic = 7;

		GraphNode closestMatch=null;
		int minScore = Integer.MAX_VALUE;

		for (GraphNode node: graphSet){

			// Exact match
			if (originalNode.getValue().getPrimaryEmail()
							.equalsIgnoreCase(node.getValue().getPrimaryEmail())){
				return node;
			}

			// New Node => Will always have one email
			String originalEmail = originalNode.getValue()
																								.getPrimaryEmail().split("@enron.com")[0];
			List<String> matchEmails = node.getValue()
							.getEmails()
							.stream().map(email -> email.split("@enron.com")[0])
							.collect(Collectors.toList());

			int nodeScore =Integer.MAX_VALUE;
			for (String matchEmail: matchEmails){
				int emailScore = findMinEditDistance(originalEmail, matchEmail);
				nodeScore = nodeScore<emailScore? nodeScore:emailScore;
			}

			if (nodeScore<minScore){
				minScore = nodeScore;
				closestMatch = node;
			}
		}

		if (minScore<= matchValueHeuristic){
			return closestMatch;
		}

		return null;
	}
}
