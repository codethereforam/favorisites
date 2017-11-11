package team.soth.favorisites.web.validator;

import com.baidu.unbiz.fluentvalidator.ValidationError;
import com.baidu.unbiz.fluentvalidator.Validator;
import com.baidu.unbiz.fluentvalidator.ValidatorContext;
import com.baidu.unbiz.fluentvalidator.ValidatorHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import team.soth.favorisites.dao.model.UserExample;
import team.soth.favorisites.service.UserService;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 检查用户field是否已经存在于数据库
 * Created by thinkam on 17-11-8.
 */
public class UserFieldExistValidator extends ValidatorHandler<String> implements Validator<String> {
	private UserService userService;
	private String field;
	private String errorMsg;

	private static Logger logger = LoggerFactory.getLogger(UserFieldExistValidator.class);

	public UserFieldExistValidator(UserService userService, String field, String errorMsg) {
		this.userService = userService;
		this.field = field;
		this.errorMsg = errorMsg;
	}

	@Override
	public boolean validate(ValidatorContext context, String s) {
		if (s == null) {
			return false;
		}
		UserExample userExample = new UserExample();
		UserExample.Criteria criteria = userExample.createCriteria();
		//.andEmailEqualTo(s)
		String methodName = "and" + field.substring(0, 1).toUpperCase() + field.substring(1, field.length()) + "EqualTo";
		try {
			Method method = UserExample.Criteria.class.getDeclaredMethod(methodName, String.class);
			method.invoke(criteria, s);
		} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			logger.error("方法反射调用错误", e);
		}
		if (userService.countByExample(userExample) > 0) {
			context.addError(
					ValidationError.create(errorMsg)
							.setErrorCode(0)
							.setField(field)
							.setInvalidValue(s));
			return false;
		}
		return true;
	}

}
