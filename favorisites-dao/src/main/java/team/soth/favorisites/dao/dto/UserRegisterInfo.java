package team.soth.favorisites.dao.dto;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;
import org.mybatis.generator.api.dom.java.Interface;

import javax.validation.GroupSequence;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * 用户提交的注册信息
 * Created by thinkam on 17-11-1.
 */
public class UserRegisterInfo {
	/**
	 * 用户名
	 */
	@NotEmpty(message = "此处不能留空", groups = Register.class)
	@Size(min = 5, max = 15, message = "长度应为5-15个字符，请勿包含姓名/身份证/银行卡等隐私信息", groups = Register.class)
	@Pattern(regexp = "[0-9a-zA-Z\u4e00-\u9fa5_]+", message = "用户名仅支持中英文、数字和下划线", groups = Register.class)
	private String username;

	/**
	 * 邮箱
	 */
	@NotEmpty(message = "此处不能留空", groups = {Register.class, GetEmailCaptcha.class})
	@Size(max = 50, message = "邮箱长度不能超过50", groups = {Register.class, GetEmailCaptcha.class})
	@Email(message = "邮箱格式不符合要求", groups = {Register.class, GetEmailCaptcha.class})
	private String email;

	/**
	 * 性别(0:女，1:男，2:不愿透露)
	 */
	@NotNull(message = "此处不能留空", groups = Register.class)
	@Range(min = 0, max = 2, message = "请选择给定的性别", groups = Register.class)
	private Byte sex;

	/**
	 * 密码
	 */
	@NotBlank(message = "此处不能留空", groups = Register.class)
	@Size(min = 6, max = 16, message = "长度应为6-16个字符", groups = Register.class)
	@Pattern(regexp = "[0-9a-zA-Z\\p{Punct} ”]+", message = "密码仅支持字母、数字及标点符号", groups = Register.class)
	private String password;

	/**
	 * 确认密码
	 */
	@NotBlank(message = "此处不能留空", groups = Register.class)
	private String confirmedPassword;

	/**
	 * 邮箱验证码
	 */
	@NotEmpty(message = "此处不能留空", groups = Register.class)
	private String emailCaptcha;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Byte getSex() {
		return sex;
	}

	public void setSex(Byte sex) {
		this.sex = sex;
	}

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

	public String getEmailCaptcha() {
		return emailCaptcha;
	}

	public void setEmailCaptcha(String emailCaptcha) {
		this.emailCaptcha = emailCaptcha;
	}

	@Override
	public String toString() {
		return "UserRegisterInfo{" +
				"username='" + username + '\'' +
				", email='" + email + '\'' +
				", sex=" + sex +
				", password='" + password + '\'' +
				", confirmedPassword='" + confirmedPassword + '\'' +
				", emailCaptcha='" + emailCaptcha + '\'' +
				'}';
	}
}
