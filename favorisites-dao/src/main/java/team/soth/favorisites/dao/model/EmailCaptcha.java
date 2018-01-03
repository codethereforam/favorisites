package team.soth.favorisites.dao.model;

import team.soth.favorisites.common.util.EmailUtil;

import java.util.UUID;

/**
 * 邮箱验证码
 * Created by thinkam on 17-11-3.
 */
public class EmailCaptcha {
	public static final int LENGTH = 6;

	/**
	 * 失效时间（单位 s）
	 */
	public static final long EXPIRED_TIME = 60;
	/**
	 * 同一email每天限制3次
	 */
	public static final int EMAIL_LIMIT = 3;

	/**
	 * 同一ip每天限制6次
	 */
	public static final int IP_LIMIT = 6;

	private String value;

	public EmailCaptcha() {
		this.value = randomValue();
	}

	private String randomValue() {
		return UUID.randomUUID().toString().replace("-", "").substring(0, LENGTH);
	}

	public String getValue() {
		return this.value;
	}

	public boolean sendEmailCaptcha(String email, String emailCaptchaValue) {
		return EmailUtil.send("favorisites用户", email,
				emailCaptchaValue+ "是您的favorisites验证码",
				"<div style=\"text-align: center;color:dodgerblue;\">欢迎注册使用favoristes， 您的验证码是" + emailCaptchaValue + "</div>");
	}
}
