package team.soth.favorisites.web.controller;

import com.baidu.unbiz.fluentvalidator.ComplexResult;
import com.baidu.unbiz.fluentvalidator.FluentValidator;
import com.baidu.unbiz.fluentvalidator.jsr303.HibernateSupportedValidator;
import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import team.soth.favorisites.common.util.ResultUtil;
import team.soth.favorisites.dao.dto.UserLoginInfo;
import team.soth.favorisites.dao.model.User;
import team.soth.favorisites.dao.model.UserExample;
import team.soth.favorisites.service.UserService;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Validation;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import static com.baidu.unbiz.fluentvalidator.ResultCollectors.toComplex;

/**
 * 用户登录控制器
 * Created by thinkam on 17-11-8.
 */
@RestController
@Api(value = "登录管理", description = "登录管理")
public class LoginController {
	private static final String IS_LOGIN = "is_login";
	private static final String TRUE = "true";
	private static final String FORGET_PASSWORD_USER = "forget_password_user";
	private static Logger logger = LoggerFactory.getLogger(UserService.class);
	@Autowired
	private UserService userService;

	@Autowired
	private Producer captchaProducer;


	@ApiOperation("登录")
	@PostMapping("/sessions")
	public ComplexResult login(@RequestBody UserLoginInfo userLoginInfo, HttpSession session) {
		//log记录信息
		logger.debug("method login get param:" + userLoginInfo);
		//通过session判断是否已登录
		if (TRUE.equals(session.getAttribute(IS_LOGIN))) {
			return ResultUtil.getComplexErrorResult("您已登录！");
		}
		Subject subject = SecurityUtils.getSubject();
		//获取参数并trim必要的参数
		String accountName = StringUtils.trim(userLoginInfo.getAccountName());
		String password = userLoginInfo.getPassword();
		String captcha = StringUtils.trim(userLoginInfo.getCaptcha());
		//验证验证码是否正确
		if(captcha == null) {
			return ResultUtil.getComplexErrorResult(0, "请输入验证码", "captcha", userLoginInfo.getCaptcha());
		}
		Object kaptchaSessionKey = session.getAttribute(Constants.KAPTCHA_SESSION_KEY);
		if(kaptchaSessionKey == null || !captcha.equalsIgnoreCase((String) kaptchaSessionKey)) {
			return ResultUtil.getComplexErrorResult(0, "验证码错误", "captcha", userLoginInfo.getCaptcha());
		}
		boolean rememberMe = userLoginInfo.isRememberMe();
		//参数验证，（返回结果）
		javax.validation.Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		ComplexResult result = FluentValidator.checkAll()
				.failOver()
				.on(userLoginInfo, new HibernateSupportedValidator<UserLoginInfo>().setHiberanteValidator(validator))
				.doValidate()
				.result(toComplex());
		if (!result.isSuccess()) {
			return result;
		}
		//使用shiro认证并返回结果
		UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(accountName, password);
		try {
			if (rememberMe) {
				usernamePasswordToken.setRememberMe(true);
			} else {
				usernamePasswordToken.setRememberMe(false);
			}
			subject.login(usernamePasswordToken);
		} catch (UnknownAccountException | IncorrectCredentialsException e) {
			return ResultUtil.getComplexErrorResult("你输入的密码和账户名不匹配");
		} catch (LockedAccountException e) {
			return ResultUtil.getComplexErrorResult("帐号已锁定，请联系管理员");
		}
		//session记录登录信息
		session.setAttribute(IS_LOGIN, TRUE);
		return ResultUtil.getComplexSuccessResult();
	}

	@ApiOperation("退出登录")
	@DeleteMapping("/sessions")
	public ComplexResult logout(HttpSession session) {
		//TODO:shiro session problem
		// shiro退出登录
//		SecurityUtils.getSubject().logout();
		//session移除登录信息
		session.removeAttribute(IS_LOGIN);
		logger.debug(IS_LOGIN + ":" + session.getAttribute(IS_LOGIN));
		return ResultUtil.getComplexSuccessResult();
	}

	@ApiOperation("获取图片验证码")
	@GetMapping("/captchas")
	public void getCaptcha(HttpServletResponse response, HttpSession session) {
		response.setDateHeader("Expires", 0);
		// Set standard HTTP/1.1 no-cache headers.
		response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
		// Set IE extended HTTP/1.1 no-cache headers (use addHeader).
		response.addHeader("Cache-Control", "post-check=0, pre-check=0");
		// Set standard HTTP/1.0 no-cache header.
		response.setHeader("Pragma", "no-cache");
		response.setContentType("image/jpeg");
		// create the text for the image
		String capText = captchaProducer.createText();
		// store the text in the session
		session.setAttribute(Constants.KAPTCHA_SESSION_KEY, capText);
		// create the image with the text
		BufferedImage bi = captchaProducer.createImage(capText);
		// write the data out
		ServletOutputStream out = null;
		try {
			out = response.getOutputStream();
			ImageIO.write(bi, "jpg", out);
			out.flush();
		} catch (IOException e) {
			//TODO:处理系统错误，记录日志 & 发邮件给管理员
		} finally {
			try {
				if(out != null) {
					out.close();
				}
			} catch (IOException e) {
				//TODO:处理系统错误，记录日志 & 发邮件给管理员
			}
		}
	}

	/*@ApiOperation("忘记密码")
	@PostMapping("/users/{accountName}/forget_password")
	public ComplexResult forgetPassword(@PathVariable String accountName, HttpSession session) {
		//log记录accountName
		logger.debug("method forgetPassword get accountName:" + accountName);
		//trim accountName
		accountName = StringUtils.trim(accountName);
		//根据accountName查询用户
		if(StringUtils.isBlank(accountName)) {
			return ResultUtil.getComplexErrorResult("用户名或邮箱不存在");
		}
		UserExample userExample = new UserExample();
		UserExample.Criteria criteria1 = userExample.createCriteria();
		criteria1.andUsernameEqualTo(accountName);
		UserExample.Criteria criteria2 = userExample.createCriteria();
		criteria2.andEmailEqualTo(accountName);
		userExample.or(criteria2);
		List<User> users = userService.selectByExample(userExample);
		User user = users.size() == 0 ? null : users.get(0);
		if(user == null) {
			return ResultUtil.getComplexErrorResult("用户名或邮箱不存在");
		}
		//生成并发送邮箱验证码
		ComplexResult result = new RegisterController().getEmailCaptcha(user.getEmail(), session);
		if(result.isSuccess()) {
			session.setAttribute(FORGET_PASSWORD_USER, user);
		}
		return result;
	}

	//TODO:重置密码
	@ApiOperation("重置密码")
	@PostMapping("/users/{accountName}/reset_password")
	public ComplexResult resetPassword(@PathVariable String accountName) {

		return ResultUtil.getComplexSuccessResult();
	}*/
}
