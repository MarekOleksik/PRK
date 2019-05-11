package application;

/**
*
* @author Krzysztof Jagodziński
*/
public class User {
	String picID;
	// nazwa obrazka wybrana przez użytkownika
	String userName;
	// nazwa wybrana przez użytkownika
	int UID;
	// identyfikator użytkownika nadany przez serwer
	
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPicID() {
		return picID;
	}
	public void setPicID(String picID) {
		this.picID = picID;
	}
	public int getUID() {
		return UID;
	}
	public void setUID(int UID) {
		this.UID = UID;
	}
}
