package team.soth.favorisites.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

/**
 * 邮件工具类
 * Created by thinkam on 17-11-3.
 */
public class EmailUtil {
	private static PropertiesFileUtil prop = PropertiesFileUtil.getInstance("email");

	private static String SENDER_ACCOUNT = prop.get("sender.account");
	private static String SENDER_PASSWORD = prop.get("sender.password");
	private static String SENDER_SMTP_HOST = prop.get("sender.smtp.host");
	private static String SENDER_NAME = prop.get("sender.name");

	private static boolean DEBUG = Boolean.valueOf(prop.get("debug"));

	private static final String EMAIL_SEND_ERROR = "邮件发送出错";

	private static Logger logger = LoggerFactory.getLogger(EmailUtil.class);


	public static boolean send(String receiverName, String receiverAccount, String subject, String content) {
		Properties props = System.getProperties();
		props.setProperty("mail.transport.protocol", "smtp");
		props.setProperty("mail.smtp.host", SENDER_SMTP_HOST);
		props.setProperty("mail.smtp.auth", "true");

		Session session = Session.getDefaultInstance(props);
		// 设置为debug模式, 可以查看详细的发送log
		session.setDebug(DEBUG);

		Transport transport = null;
		try {
			MimeMessage message = createMimeMessage(session, receiverName, receiverAccount, subject, content);
			transport = session.getTransport();
			transport.connect(SENDER_ACCOUNT, SENDER_PASSWORD);
			transport.sendMessage(message, message.getAllRecipients());
		} catch (UnsupportedEncodingException | MessagingException e) {
			logger.error(EMAIL_SEND_ERROR, e);
			return false;
		} finally {
			try {
				if (transport != null) {
					transport.close();
				}
			} catch (MessagingException e) {
				logger.error(EMAIL_SEND_ERROR, e);
			}
		}
		return true;
	}

	/**
	 * 创建一封只包含html的简单邮件
	 */
	private static MimeMessage createMimeMessage(Session session, String receiverName, String receiverAccount, String subject, String content) throws UnsupportedEncodingException, MessagingException {
		MimeMessage message = new MimeMessage(session);
		message.setFrom(new InternetAddress(SENDER_ACCOUNT, SENDER_NAME, "UTF-8"));
		message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiverAccount, receiverName, "UTF-8"));
		message.setSubject(subject, "UTF-8");
		message.setContent(content, "text/html;charset=UTF-8");
		message.setSentDate(new Date());
		message.saveChanges();
		return message;
	}

}
