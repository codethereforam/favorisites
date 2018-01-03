package team.soth.favorisites.dao.dto;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * 用户登录信息
 * Created by thinkam on 17-11-9.
 */
public class UserLoginInfo {
	/**
	 * 账户名：用户名/邮箱
	 */
	@NotEmpty(message = "请填写账户名")
	private String accountName;

	/**
	 * 密码
	 */
	@NotBlank(message = "请输入密码")
	private String password;

	/**
	 * 验证码
	 */
	private String captcha;

	/**
	 * 记住密码
	 */
	private boolean rememberMe;

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCaptcha() {
		return captcha;
	}

	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}

	public boolean isRememberMe() {
		return rememberMe;
	}

	public void setRememberMe(boolean rememberMe) {
		this.rememberMe = rememberMe;
	}

	@Override
	public String toString() {
		return "UserLoginInfo{" +
				"accountName='" + accountName + '\'' +
				", password='" + password + '\'' +
				", captcha='" + captcha + '\'' +
				", rememberMe=" + rememberMe +
				'}';
	}
}
