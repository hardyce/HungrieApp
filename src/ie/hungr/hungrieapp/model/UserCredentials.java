//Date: 23/01/2013
//Written By: Seamus and Matt
//Desc: The is a class responsible for user credential information. It converts it into json and encrypts it

package ie.hungr.hungrieapp.model;

public class UserCredentials {
	private String username, password;

	public UserCredentials(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
