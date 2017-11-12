package team.soth.favorisites.web.validator;

import com.baidu.unbiz.fluentvalidator.ValidationError;
import com.baidu.unbiz.fluentvalidator.Validator;
import com.baidu.unbiz.fluentvalidator.ValidatorContext;
import com.baidu.unbiz.fluentvalidator.ValidatorHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by thinkam on 17-11-12.
 */
public class PasswordMatchValidator extends ValidatorHandler<String> implements Validator<String> {
	private String password;
	private String field;
	private String errorMsg;

	public PasswordMatchValidator(String password, String field, String errorMsg) {
		this.password = password;
		this.field = field;
		this.errorMsg = errorMsg;
	}

	private static Logger logger = LoggerFactory.getLogger(UserFieldExistValidator.class);

	@Override
	public boolean validate(ValidatorContext context, String s) {
		if (s == null) {
			return false;
		}
		if (!s.equals(password)) {
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
