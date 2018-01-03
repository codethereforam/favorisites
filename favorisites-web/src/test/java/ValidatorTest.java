import com.baidu.unbiz.fluentvalidator.*;
import com.baidu.unbiz.fluentvalidator.jsr303.HibernateSupportedValidator;
import team.soth.favorisites.dao.dto.UserRegisterInfo;

import javax.validation.Validation;

import static com.baidu.unbiz.fluentvalidator.ResultCollectors.toComplex;

/**
 * Created by thinkam on 17-11-1.
 */
public class ValidatorTest {
	public static void main(String[] args) {
		UserRegisterInfo userRegisterInfo = new UserRegisterInfo();
		userRegisterInfo.setUsername("  ");
		userRegisterInfo.setPassword("p1");

		javax.validation.Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		GenericResult result = FluentValidator.checkAll()
				.failOver()
				.on(userRegisterInfo, new HibernateSupportedValidator<UserRegisterInfo>().setHiberanteValidator(validator))
				.on(userRegisterInfo.getUsername(), new ValidatorHandler<String>() {
					@Override
					public boolean validate(ValidatorContext context, String s) {
						if(s.equals("u1")) {
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
				.doValidate()
				.result(toComplex());
//		System.out.println(result);
		result.getErrors().forEach(System.out::println);
	}

}
