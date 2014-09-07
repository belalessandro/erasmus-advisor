package it.unipd.dei.bding.erasmusadvisor.test;

import it.unipd.dei.bding.erasmusadvisor.beans.IscrizioneBean;
import it.unipd.dei.bding.erasmusadvisor.beans.ResponsabileFlussoBean;
import it.unipd.dei.bding.erasmusadvisor.beans.StudenteBean;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.postgresql.util.Base64;

public class Populate {
	
	
	private static String hashPassword(String password, String salt) {
		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance("SHA-256");
			String salted = password + salt;
			try {
				byte[] hash = digest.digest(salted.getBytes("UTF-8"));
				//return new String(hash, "UTF-8");
				String base64 = Base64.encodeBytes(hash); // Fix issue 23
			    return base64;
			} 
			catch (UnsupportedEncodingException e) {
				throw new IllegalStateException();
			}
		} 
		catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException();
		}
	}
	
	public static void main(String[] args) {
		String csv = "/Users/mauro/Desktop/applications.csv";
		BufferedReader buf = null;
		
		String line = "";
		
		String csvSplit = ";";
		
		/**
		 * Cities
		 */
		
		
		try {
			Map<String, String> map = new HashMap<String, String>();
			
			buf = new BufferedReader(new FileReader(csv));
			
			/**
			 * Cities
			 */
//			while ((line = buf.readLine()) != null) {
//				 
//				// use comma as separator
//				String[] place = line.split(csvSplit);
//				String city = place[4].substring(0, 1).toUpperCase() + place[4].substring(1).toLowerCase();
//				String country = place[5].substring(0, 1).toUpperCase() + place[5].substring(1).toLowerCase();
//				map.put(country,city);
//			}
//			
//			
			String sqlCities = "/Users/mauro/Desktop/cities.sql";
			FileWriter writer = new FileWriter(sqlCities);
//			
//			
//			//loop map
//			for (Map.Entry<String, String> entry : map.entrySet()) {
//	 
//				writer.write("INSERT INTO Citta VALUES ('" + entry.getValue()  + "', '" + entry.getKey() + "');\n");
//	 
//			}
			writer.flush();
			writer.close();
//			
//			buf.close();
			
			
			/**
			 * Flow Managers
			 */
			
			csv = "/Users/mauro/Desktop/Ingegneria 1.csv";
			Map<String, ResponsabileFlussoBean> mapManager = new HashMap<String, ResponsabileFlussoBean>();
				
			buf = new BufferedReader(new FileReader(csv));
			
			String surname = null;
			String name = null;
			String username = null;
			String email = null;
			String salt = null;
			String password = null;
			
			ResponsabileFlussoBean manager = new ResponsabileFlussoBean();
			String[] professor = null;
			while ((line = buf.readLine()) != null) {
				 
				// use comma as separator
				String[] record = line.split(csvSplit);
				
				
				try {
					professor = record[7].split(" ");
					surname = professor[0].trim();
					surname = surname.substring(0,1).toUpperCase() + surname.substring(1).toLowerCase();
					name = professor[1].trim();
					username = professor[0].trim().toLowerCase();
					email = username + "@unipd.it";
					 
					// settin the password
					SecureRandom random = new SecureRandom();
					salt = "" + random.nextLong();
					password = hashPassword("aaaaaa", salt);
					
					manager = new ResponsabileFlussoBean();
					manager.setAbilitato(true);
					manager.setAttivo(true);
					manager.setCognome(surname);
					manager.setNome(name);
					manager.setEmail(email);
					manager.setNomeUtente(username);
					manager.setNomeUniversita("Universita degli Studi di Padova");
					manager.setPassword(password);
					manager.setSalt(salt);
					
					
					mapManager.put(username, manager);
				} catch(ArrayIndexOutOfBoundsException e)
				{
					System.out.println("Buh");
				}
				
				
				

				
			}	
			
			String sqlFlowManagers = "/Users/mauro/Desktop/flowmanagers.sql";
			writer = new FileWriter(sqlCities);
			
			
			//loop map
			for (Entry<String, ResponsabileFlussoBean> entry : mapManager.entrySet()) {
				
				
				System.out.println("INSERT INTO ResponsabileFlusso VALUES ('"
						+ entry.getKey() + "', '"
						+ entry.getValue().getNome() +"', '"
						+ entry.getValue().getCognome() + "', '"
						+ entry.getValue().getEmail() + "', 'current_date', '"
						+ entry.getValue().getPassword() + "', '"
						+ entry.getValue().getSalt() + "', 'current_date', true, true, '"
						+ entry.getValue().getNomeUniversita() + "');");
//				writer.write("INSERT INTO ResponsabileFlusso VALUES ('Mike', 'Michele', 'Santiapostoli', 'michele@unipd.it', '2014-09-06', 'CZlXeOhbvS1N6/kBYxwzl9sHEkQ4s4HgqP1meX0erww=', '969306631910606608', '2014-09-06', true, true, 'Universita degli Studi di Padova');\n");
	 
			}
			writer.flush();
			writer.close();
			
			
			buf.close();
			
			/**
			 * students
			 */
			
			csv = "/Users/mauro/Desktop/studenti.csv";
			Map<String, StudenteBean> mapStudent = new HashMap<String, StudenteBean>();
			Map<String, IscrizioneBean> mapSub = new HashMap<String, IscrizioneBean>();
					
				
			buf = new BufferedReader(new FileReader(csv));
			
			username = null;
			email = null;
			salt = null;
			password = null;
			
			StudenteBean student = new StudenteBean();
			IscrizioneBean iscrizione = new IscrizioneBean();
			
			// settin the password
			SecureRandom random = new SecureRandom();
			salt = "" + random.nextLong();
			password = hashPassword("aaaaaa", salt);
			
			while ((line = buf.readLine()) != null) {
				 
				// use comma as separator
				String[] record = line.split(csvSplit);
				
				
				try {
					
					 

					
					student = new StudenteBean();
					iscrizione = new IscrizioneBean();
					
					student.setEmail(record[0] + "@studenti.unipd.it");
					student.setAttivo(true);
					student.setNomeUtente(record[1]);
					student.setPassword(password);
					student.setSalt(salt);
					
					
					
					iscrizione.setIdCorso(Integer.parseInt(record[2]));
					iscrizione.setNomeUtenteStudente(record[1]);
					iscrizione.setAnnoFine(Date.valueOf(record[4]));
					iscrizione.setAnnoFine(Date.valueOf(record[5]));
					
				} catch(ArrayIndexOutOfBoundsException e)
				{
					System.out.println("Buh");
				}
				System.out.println("INSERT INTO Studente VALUES ('" 
				+ student.getNomeUtente() + "', '" 
				+ student.getEmail() + "', current_date,'"
				+ student.getPassword() + "','"
				+ student.getSalt() + "', current_date, 't');");
				
				System.out.println("INSERT INTO Iscrizione VALUES ('"
						+ iscrizione.getIdCorso() + "','" 
						+ iscrizione.getNomeUtenteStudente() + "','2014-10-01','"
						+ iscrizione.getAnnoFine() + "');");
			}	
			
			buf.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
