package com.social.network.utils;


import com.social.network.model.Person;

import javax.mail.Address;
import javax.mail.Header;
import javax.mail.internet.MimeMessage;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;


public class EMLParser {

	private Person sender;
	private Set<Person> recipientList;

	public EMLParser(String path) throws Exception{
		this.recipientList = new HashSet<>();
		this.readEMLFile(path);

	}

	/**
	 * return Sender
	 * @return
	 */
	public Person getSender(){
		return this.sender;
	}

	public Set<Person> getRecipientList(){
		return this.recipientList;
	}

	/**
	 * Read the email, parse and update the Sender and recipientList.
	 * @param path
	 * @throws Exception
	 */
	private void readEMLFile(String path) throws Exception{
		try(InputStream is = new FileInputStream(path)) {
			MimeMessage mime = new MimeMessage(null, is);

			String senderEmail = mime.getFrom()[0].toString();
			Address[] recipients = mime.getAllRecipients();
			sender = new Person(senderEmail);

			if(recipients!=null)
				recipientList = Arrays.asList(recipients)
								.stream()
								.map(Address::toString)
								.map(obj -> new Person(obj))
								.collect(Collectors.toSet());

			/**
			 * If there are multiple emails for same person, we assume `Name` as the unique.
			 * We then group emails for people with same name
			 * So below we extract Names for both sender and recipient.
			 */

//				String senderName="";
//				List<String> recipientNames = new ArrayList<>();
//				for (Enumeration<Header> e = mime.getAllHeaders(); e.hasMoreElements(); ) {
//						Header h = e.nextElement();
//						// Get the Sender Name
//						if (h.getName().equals("X-From")) {
//										senderName = h.getValue();
//						}
//						// Get list of recipient Name
//						 if (h.getName().equals("X-To")) {
//								recipientNames = Arrays.asList(h.getValue().split(","))
//																				.stream().map(String::trim)
//																				.filter(str -> (
//																								str.split("@").length == 1 ||  // Either Name
//																								(Utilities.isValidEmail(str)))       // Email IDs are allowed as Name
//										 )
//										.collect(Collectors.toList());
//						}
//				}
//				this.sender = new Person(senderName, senderEmail);
//				this.recipientList = Person.getPersonList(recipientNames, recipientEmails);

		}
		catch (Exception e){
			System.out.println("Failed to parse eml file: "+ path +"\n"+ e.getMessage());
			Utilities.writeToFile(path, "/Users/sverma/Documents/saurav-verma/data/outputs/failed-files.txt");
		}
	}
}
