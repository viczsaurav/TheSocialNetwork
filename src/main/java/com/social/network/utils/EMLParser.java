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
		String emailMatch = "enron.com";
		try(InputStream is = new FileInputStream(path)) {
			MimeMessage mime = new MimeMessage(null, is);

			String senderEmail = mime.getFrom()[0].toString();
      
      if (senderEmail.contains(emailMatch) && Utilities.isValidEmail(senderEmail)) {
				Address[] recipients = mime.getAllRecipients();
				this.sender = new Person(senderEmail);

        if(recipients!=null)
					this.recipientList = Arrays.asList(recipients)
                  .stream()
                  .map(Address::toString)
									.filter(email -> email.contains(emailMatch))
									.map(email -> new Person(email))
                  .collect(Collectors.toSet());
			}
		}
		catch (Exception e){
			System.out.println("Failed to parse eml file: "+ path +"\n"+ e.getMessage());
			Utilities.writeToFile(path, "/Users/sverma/Documents/saurav-verma/data/outputs-2/failed-files-multi-emails.csv");
		}
	}
}
