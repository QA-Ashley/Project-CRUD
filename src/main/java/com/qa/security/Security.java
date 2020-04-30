package main.java.com.qa.security;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Security {
	public String hashPassword(String pass) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		byte[] hashInBytes = md.digest(pass.getBytes(StandardCharsets.UTF_8));

		StringBuilder sb = new StringBuilder();
		for (byte b : hashInBytes) {
			sb.append(String.format("%02x", b));
		}
		return sb.toString();
	}
	
	public String sanitizeStrings(String input) {
		/*
		 *  Implement method to read the input and sanitize it for safety.
		 */
		return input;
	}
}
