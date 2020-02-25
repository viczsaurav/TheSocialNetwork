package com.social.network.main;

import com.social.network.model.SocialGraph;
import com.social.network.utils.EMLParser;
import com.social.network.utils.Utilities;

import java.io.File;
import java.util.Map;
import java.util.Set;

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
	private static String failedFiles;

	private static ClassLoader classLoader;

	public static void main(String[] args) throws Exception{
		ApplicationMain main = new ApplicationMain();
		classLoader = main.getClass().getClassLoader();
		Map<String, String> configMap  = Utilities.initProperties(classLoader);

		downloadedFilePath = configMap.get("downloaded.filePath");
		downloadedFileName = configMap.get("downloaded.fileName");
		blacklistFilename = configMap.get("blacklist.filename");
		outputEdgeListFilename = configMap.get("output.edge-list.filename");
		failedFiles = configMap.get("failed.files");

		if (downloadedFilePath==null || downloadedFileName==null ||
						blacklistFilename==null || outputEdgeListFilename==null){
			throw new Exception("Please check the config file.. inputs missing..");
		}
			main.setup();
			System.out.println("Size of Network : "+ SocialGraph.getSocialNetworkSize());
			Utilities.writeEdgesToFile(SocialGraph.getSocialGraph(), outputEdgeListFilename);
	}

	private void setup() throws Exception{

		File blackListFile = new File(classLoader.getResource(blacklistFilename).getFile());
		Set<String> blackList = Utilities.getFileList(blackListFile, downloadedFilePath);

		File downloadedFileList= new File(classLoader.getResource(downloadedFileName).getFile());
		Set<String> fileList = Utilities.getFileList(downloadedFileList, downloadedFilePath);

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
				System.out.println("\nFile processing Failed: "+ path + "\n Error: " + e.getMessage());
			}
		});
	}


}
