package team.soth.favorisites.web.controller;

import com.baidu.unbiz.fluentvalidator.*;
import com.baidu.unbiz.fluentvalidator.jsr303.HibernateSupportedValidator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import team.soth.favorisites.common.util.MD5Util;
import team.soth.favorisites.common.util.ResultUtil;
import team.soth.favorisites.dao.dto.GetEmailCaptcha;
import team.soth.favorisites.dao.dto.Register;
import team.soth.favorisites.dao.dto.UserRegisterInfo;
import team.soth.favorisites.dao.model.EmailCaptcha;
import team.soth.favorisites.dao.model.User;
import team.soth.favorisites.service.UserService;
import team.soth.favorisites.web.validator.PasswordMatchValidator;
import team.soth.favorisites.web.validator.UserFieldExistValidator;

import javax.servlet.http.HttpSession;
import javax.validation.Validation;
import java.util.Date;
import java.util.UUID;

import static com.baidu.unbiz.fluentvalidator.ResultCollectors.toComplex;

/**
 * 用户注册控制器
 * Created by thinkam on 17-11-1.
 */
//TODO:接受到不符合规范的json处理
@RestController
@Api(value = "注册管理", description = "注册管理")
public class RegisterController {
	public static final String EMAIL_CAPTCHA = "email_captcha";
	private static Logger logger = LoggerFactory.getLogger(UserService.class);
	@Autowired
	private UserService userService;

	/*
	 * restful接口测试
	 * headers： "key":"Content-Type" "value":"application/json"
	 * body：
		{
			"username": "thinkam",
			"email": "1203948298@qq.com",
			"sex": 1,
			"password": "978299",
			"confirmedPassword": "978299",
			"emailCaptcha": "123456"
		}
	 */
	@ApiOperation(value = "用户注册")
	@PostMapping("/users")
	public ComplexResult register(@RequestBody UserRegisterInfo userRegisterInfo, HttpSession session) {
		logger.debug("method register get param:" + userRegisterInfo);
		//trim必要的String
		userRegisterInfo.setUsername(StringUtils.trim(userRegisterInfo.getUsername()));
		userRegisterInfo.setEmail(StringUtils.trim(userRegisterInfo.getEmail()));
		userRegisterInfo.setEmailCaptcha(StringUtils.trim(userRegisterInfo.getEmailCaptcha()));
		//数据验证,(返回结果)
		javax.validation.Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		ComplexResult result = FluentValidator.checkAll(new Class<?>[]{Register.class})
				.failOver()
				.on(userRegisterInfo, new HibernateSupportedValidator<UserRegisterInfo>().setHiberanteValidator(validator))
				.on(userRegisterInfo.getUsername(), new UserFieldExistValidator(userService, "username", "该用户名已有人使用"))
				.on(userRegisterInfo.getEmail(), new UserFieldExistValidator(userService, "email", "该邮箱已有人使用"))
				.on(userRegisterInfo.getConfirmedPassword(), new PasswordMatchValidator(userRegisterInfo.getPassword(), "confirmedPassword", "两个密码不匹配"))
				.on(userRegisterInfo.getEmailCaptcha(), new ValidatorHandler<String>() {
					@Override
					public boolean validate(ValidatorContext context, String s) {
						if (s == null) {
							return false;
						}
						ValidationError error = ValidationError.create("验证码错误，请检查邮箱地址或点击重新发送")
								.setErrorCode(0)
								.setField("emailCaptcha")
								.setInvalidValue(s);
						Object emailCaptcha = session.getAttribute(EMAIL_CAPTCHA);
						if (emailCaptcha == null || !s.equalsIgnoreCase((String) emailCaptcha)) {
							context.addError(error);
							return false;
						}
						return true;
					}
				})
				.doValidate()
				.result(toComplex());
		if (!result.isSuccess()) {
			return result;
		}
		//封装并持久化User
		User user = new User();
		user.setUsername(userRegisterInfo.getUsername());
		String salt = UUID.randomUUID().toString().replaceAll("-", "");
		user.setSalt(salt);
		user.setPassword(MD5Util.MD5(userRegisterInfo.getPassword() + salt));
		user.setEmail(userRegisterInfo.getEmail());
		user.setSex(userRegisterInfo.getSex());
		user.setLocked((byte) 0);
		user.setCreateTime(new Date());
		int lastInsertId = userService.insert(user);
		if (lastInsertId <= 0) {
			result = ResultUtil.getComplexErrorResult(1000, "系统后台出错");
			//TODO:处理系统错误，记录日志 & 发邮件给管理员
		}
		//返回结果
		return result;
	}

	@ApiOperation(value = "获取邮箱验证码")
	@GetMapping("/emails/{email}/emailCaptchas")
	public ComplexResult getEmailCaptcha(@PathVariable String email, HttpSession session) {
		logger.debug("method getEmailCaptcha get email:" + email);
		//TODO:发送条件检查
		//trim email
		email = StringUtils.trim(email);
		//邮箱格式验证,(返回结果)
		UserRegisterInfo userRegisterInfo = new UserRegisterInfo();
		userRegisterInfo.setEmail(email);
		javax.validation.Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		ComplexResult result = FluentValidator.checkAll(new Class<?>[]{GetEmailCaptcha.class})
				.failOver()
				.on(userRegisterInfo, new HibernateSupportedValidator<UserRegisterInfo>().setHiberanteValidator(validator))
				.doValidate()
				.result(toComplex());
		if (!result.isSuccess()) {
			return result;
		}
		result = this.sendEmailCaptcha(email, session);
		return result;
	}

	/**
	 * 生成并发送邮箱验证码
	 * @return complex result
	 */
	public ComplexResult sendEmailCaptcha(String email, HttpSession session) {
		//生成验证码的值
		EmailCaptcha emailCaptcha = new EmailCaptcha();
		String emailCaptchaValue = emailCaptcha.getValue();
		//保存到session
		Object emailCaptchaInSession = session.getAttribute(EMAIL_CAPTCHA);
		if (emailCaptchaInSession == null || !emailCaptchaInSession.equals(emailCaptchaValue)) {
			session.setAttribute(EMAIL_CAPTCHA, emailCaptchaValue);
			//TODO:设置验证码60s过期
		}
		//发送到邮箱
		boolean success = emailCaptcha.sendEmailCaptcha(email, emailCaptchaValue);
		//返回结果
		ComplexResult result;
		if (success) {
			result = ResultUtil.getComplexSuccessResult();
		} else {
			result = ResultUtil.getComplexErrorResult("邮件发送失败");
		}
		return result;
	}

	@ApiOperation(value = "检查用户名是否已经存在")
	@PostMapping("/usernames/{username}/check_exist")
	public ComplexResult checkUsernameExist(@PathVariable String username) {
		logger.debug("method checkUsernameExist, username:" + username);
		//check & return
		FluentValidator validator = FluentValidator.checkAll();
		if(StringUtils.isNotBlank(username)) {
			validator.on(StringUtils.trim(username), new UserFieldExistValidator(userService, "username", "该用户名已有人使用"));
		}
		return validator.doValidate().result(toComplex());
	}

	@ApiOperation(value = "检查邮箱是否已经存在")
	@PostMapping("/emails/{email}/check_exist")
	public ComplexResult checkEmailExist(@PathVariable String email) {
		logger.debug("method checkEmailExist, email:" + email);
		//check & return
		FluentValidator validator = FluentValidator.checkAll();
		if(StringUtils.isNotBlank(email)) {
			validator.on(StringUtils.trim(email), new UserFieldExistValidator(userService, "email", "该邮箱已有人使用"));
		}
		return validator.doValidate().result(toComplex());
	}

}
