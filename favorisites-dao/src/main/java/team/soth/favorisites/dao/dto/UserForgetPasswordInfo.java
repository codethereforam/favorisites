package team.soth.favorisites.dao.dto;

/**
 * Created by thinkam on 17-11-12.
 */
public class UserForgetPasswordInfo {
	private String password;
	private String confirmedPassword;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmedPassword() {
		return confirmedPassword;
	}

	public void setConfirmedPassword(String confirmedPassword) {
		this.confirmedPassword = confirmedPassword;
	}

	@Override
	public String toString() {
		return "UserForgetPasswordInfo{" +
				"password='" + password + '\'' +
				", confirmedPassword='" + confirmedPassword + '\'' +
				'}';
	}
}
