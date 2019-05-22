package application;

/**
 *
 * @author Krzysztof Jagodziński
 */
public class Sender {
	String senderName;
	// nazwa nadawcy wiadomości
	int senderUID;
	// identyfikator nadawcy
	String senderPicID;
	// nazwa obrazka nadawcy

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public int getSenderUID() {
		return senderUID;
	}

	public void setSenderUID(int senderUID) {
		this.senderUID = senderUID;
	}

	public String getSenderPicID() {
		return senderPicID;
	}

	public void setSenderPicID(String senderPicID) {
		this.senderPicID = senderPicID;
	}

}
