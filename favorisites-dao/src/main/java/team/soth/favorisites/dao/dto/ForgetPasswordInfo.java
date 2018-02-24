package team.soth.favorisites.dao.dto;

/**
 * 忘记密码用户提交的信息
 * @author thinkam
 * @date 2018/02/24
 */
public class ForgetPasswordInfo {
	private String emailCaptcha;

	@Override
	public String toString() {
		return "ForgetPasswordInfo{" +
				"emailCaptcha='" + emailCaptcha + '\'' +
				'}';
	}

	public String getEmailCaptcha() {
		return emailCaptcha;
	}

	public void setEmailCaptcha(String emailCaptcha) {
		this.emailCaptcha = emailCaptcha;
	}
}
