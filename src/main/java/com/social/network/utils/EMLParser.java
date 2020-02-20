package com.social.network.utils;


import com.social.network.model.Person;
import com.social.network.model.SocialGraph;

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
		try {
			InputStream is = new FileInputStream(path);
			MimeMessage mime = new MimeMessage(null, is);

			String senderName=null;
			String senderEmail = mime.getFrom()[0].toString();

			List<String> recipientNames = new ArrayList<>();
			List<String> recipientEmails = Arrays.asList(mime.getAllRecipients())
							.stream()
							.map(Address::toString)
							.collect(Collectors.toList());

			// The `NAME` details are only part of Mime Headers
			for (Enumeration<Header> e = mime.getAllHeaders(); e.hasMoreElements(); ) {
				Header h = e.nextElement();
				// Get the Sender Name
				if (h.getName().equals("X-From")) {
					senderName = h.getValue();
				}
				// Get list of recipient Name
				if (h.getName().equals("X-To")) {
					recipientNames = Arrays.asList(h.getValue().split(","))
									.stream().map(String::trim)
									.filter(str -> (
													str.split("@").length == 1 ||							// Email IDs are allowed as Name
													(str.split("@").length == 2 &&
													str.split("@")[1].equals("enron.com")))		// Weeding name with `@` signature
									)
									.collect(Collectors.toList());
				}
			}
			this.sender = new Person(senderName, senderEmail);
			this.recipientList = Person.getPersonList(recipientNames, recipientEmails);
		}
		catch (Exception e){
			System.out.println("Error while reading the eml file: "+ path +"\n"+ e.getMessage());
			throw e;
		}
	}
}
