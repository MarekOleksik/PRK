package application;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
/**
* Klasa testująca klasę użytkownika.
* @author Krzysztof Jagodziński
*/
public class UserTest {
	/**
	 * Metoda testuje getter i setter nazwy użytkownika
	 */
	@Test
	public void userNameTest() {
		User test = new User(); //
		test.setUserName("John");
		String result = "John";
		assertTrue(test.getUserName().equals(result)); 
		}
	/**
	 * Metoda testuje getter i setter identyfikatora użytkownika
	 */
	@Test
	public void userIDTest() {
		User test = new User(); //
		test.setUID(123456789);
		int result = 123456789;
		assertTrue(test.getUID()==result); 
		}
	/**
	 * Metoda testuje getter i setter nazwy obrazka użytkownika
	 */
	@Test
	public void picIDTest() {
		User test = new User(); //
		test.setPicID("1.jpg");
		String result = "1.jpg";
		assertTrue(test.getPicID().equals(result));  
		}
}


