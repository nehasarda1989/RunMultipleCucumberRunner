package apiEngine.model.requests;

public class AuthorizationRequest {
	public String password;
	private String userName;

	public AuthorizationRequest(String username, String password) {
		this.userName = username;
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}


}

