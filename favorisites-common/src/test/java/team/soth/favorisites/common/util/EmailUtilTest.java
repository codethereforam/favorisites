package team.soth.favorisites.common.util;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Properties;

/**
 * Created by thinkam on 17-11-3.
 */
public class EmailUtilTest {

	/*@Test
	public void send() throws Exception {
		boolean success = EmailUtil.send("favorisites用户", "1203948298@qq.com",
				"123456是您的favorisites验证码", "<div style=\"text-align: center;color:dodgerblue;\">欢迎注册使用favoristes， 您的验证码是123456</div>");
		System.out.println(success);
	}*/

	public static void main(String[] args) throws IOException {
		/*String proxyHost = "127.0.0.1";
		String proxyPort = "1080";

		System.setProperty("http.proxyHost", proxyHost);
		System.setProperty("http.proxyPort", proxyPort);

		System.setProperty("https.proxyHost", proxyHost);
		System.setProperty("https.proxyPort", proxyPort);*/

		URL url = new URL("https://www.google.com.hk");
		URLConnection connection = url.openConnection();
		connection.connect();
		InputStream inputStream = connection.getInputStream();
		byte[] bytes = new byte[1024];
		while (inputStream.read(bytes) >= 0) {
			System.out.println(new String(bytes));
		}
	}

}