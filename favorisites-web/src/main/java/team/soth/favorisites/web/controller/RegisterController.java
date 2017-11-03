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
import team.soth.favorisites.dao.dto.GetEmailCaptcha;
import team.soth.favorisites.dao.dto.Register;
import team.soth.favorisites.dao.dto.UserRegisterInfo;
import team.soth.favorisites.dao.model.EmailCaptcha;
import team.soth.favorisites.dao.model.User;
import team.soth.favorisites.dao.model.UserExample;
import team.soth.favorisites.service.UserService;

import javax.servlet.http.HttpSession;
import javax.validation.Validation;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

	private static final String EMAIL_CAPTCHA = "email_captcha";
	private static Logger logger = LoggerFactory.getLogger(UserService.class);
	@Autowired
	private UserService userService;

	/*
	 * restful接口测试
	 * headers： "key":"Content-Type" "value":"application/json"
	 * body：
		{
			"username": "u1",
			"email": "123",
			"sex": 1,
			"password": "p1",
			"confirmedPassword": "p11",
			"emailCaptcha": "456"
		}
	 */
	@ApiOperation(value = "用户注册")
	@PostMapping("/users")
	public ComplexResult register(@RequestBody UserRegisterInfo userRegisterInfo) {
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
				.on(userRegisterInfo.getUsername(), new ValidatorHandler<String>() {
					@Override
					public boolean validate(ValidatorContext context, String s) {
						if (s == null) {
							return false;
						}
						UserExample userExample = new UserExample();
						userExample.createCriteria().andUsernameEqualTo(s);
						if (userService.countByExample(userExample) > 0) {
							context.addError(
									ValidationError.create("该用户名已有人使用")
											.setErrorCode(0)
											.setField("username")
											.setInvalidValue(s));
							return false;
						}
						return true;
					}
				})
				.on(userRegisterInfo.getEmail(), new ValidatorHandler<String>() {
					@Override
					public boolean validate(ValidatorContext context, String s) {
						if (s == null) {
							return false;
						}
						UserExample userExample = new UserExample();
						userExample.createCriteria().andEmailEqualTo(s);
						if (userService.countByExample(userExample) > 0) {
							context.addError(
									ValidationError.create("该邮箱已有人使用")
											.setErrorCode(0)
											.setField("email")
											.setInvalidValue(s));
							return false;
						}
						return true;
					}
				})
				.on(userRegisterInfo.getConfirmedPassword(), new ValidatorHandler<String>() {
					@Override
					public boolean validate(ValidatorContext context, String s) {
						if (s == null) {
							return false;
						}
						if (!s.equals(userRegisterInfo.getPassword())) {
							context.addError(
									ValidationError.create("两个密码不匹配")
											.setErrorCode(0)
											.setField("confirmedPassword")
											.setInvalidValue(s));
							return false;
						}
						return true;
					}
				})
				//TODO:验证emailCaptcha是否正确
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
		System.out.println(lastInsertId);
		if (lastInsertId <= 0) {
			result.setIsSuccess(false);
			List<ValidationError> errors = new ArrayList<>();
			ValidationError validationError = new ValidationError();
			validationError.setErrorCode(1000);
			validationError.setErrorMsg("系统后台出错");
			errors.add(validationError);
			result.setErrors(errors);
			//TODO:处理系统错误，记录日志 & 发邮件给管理员
		}
		//返回结果
		return result;
	}

	@ApiOperation(value = "获取邮箱验证码")
	@GetMapping("/emails/{email}/emailCaptchas")
	public ComplexResult getEmailCaptcha(@PathVariable String email, HttpSession session) {
		logger.debug("method getEmailCaptcha get email:" + email);
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
		//生成验证码的值
		EmailCaptcha emailCaptcha = new EmailCaptcha();
		String emailCaptchaValue = emailCaptcha.getValue();
		//保存到session
		Object emailCaptchaInSession = session.getAttribute(EMAIL_CAPTCHA);
		if (emailCaptchaInSession == null || !emailCaptchaInSession.equals(emailCaptchaValue)) {
			session.setAttribute(EMAIL_CAPTCHA, emailCaptchaValue);
		}
		//发送到邮箱
		boolean success = emailCaptcha.sendEmailCaptcha(email, emailCaptchaValue);
		//返回结果
		result = new ComplexResult();
		if(success) {
			result.setIsSuccess(true);
		} else {
			result.setIsSuccess(false);
			List<ValidationError> errors = new ArrayList<>();
			ValidationError validationError = new ValidationError();
			validationError.setErrorCode(0);
			validationError.setErrorMsg("邮件发送失败");
			errors.add(validationError);
			result.setErrors(errors);
		}
		return result;
	}

	/*@GetMapping("users")
	public List<User> list() {
		return userService.selectByExample(new UserExample());
	}*/

}
