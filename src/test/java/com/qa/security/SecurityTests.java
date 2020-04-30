package test.java.com.qa.security;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import main.java.com.qa.security.Security;

import java.security.NoSuchAlgorithmException;

public class SecurityTests {
	
	Security s = new Security();
	
	@Test
	public void passwordHashTest() throws NoSuchAlgorithmException {
		assertEquals(64, s.hashPassword("password123456").length());
	}
}
