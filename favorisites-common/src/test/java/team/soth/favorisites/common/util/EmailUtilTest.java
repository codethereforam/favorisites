package team.soth.favorisites.common.util;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.net.*;
import java.util.Properties;

/**
 * Created by thinkam on 17-11-3.
 */
public class EmailUtilTest {

	@Test
	public void send() throws Exception {
		boolean success = EmailUtil.send("favorisites用户", "1203948298@qq.com",
				"123456是您的favorisites验证码", "<div style=\"text-align: center;color:dodgerblue;\">欢迎注册使用favoristes， 您的验证码是123456</div>");
		System.out.println(success);
	}

}