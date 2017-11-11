package team.soth.favorisites.common.util;

import com.baidu.unbiz.fluentvalidator.ComplexResult;
import com.baidu.unbiz.fluentvalidator.ValidationError;

import java.util.ArrayList;
import java.util.List;

/**
 * 获取结果工具类
 * Created by thinkam on 17-11-9.
 */
public class ResultUtil {

	public static ComplexResult getComplexErrorResult(String errorMsg) {
		return getComplexErrorResult(0, errorMsg, null, null);
	}

	public static ComplexResult getComplexErrorResult(int errorCode, String errorMsg) {
		return getComplexErrorResult(errorCode, errorMsg, null, null);
	}

	public static ComplexResult getComplexErrorResult(int errorCode, String errorMsg, String field, Object invalidValue) {
		ComplexResult result = new ComplexResult();
		result.setIsSuccess(false);
		List<ValidationError> errors = new ArrayList<>();
		ValidationError validationError = new ValidationError();
		validationError.setErrorCode(errorCode);
		validationError.setField(field);
		validationError.setInvalidValue(invalidValue);
		validationError.setErrorMsg(errorMsg);
		errors.add(validationError);
		result.setErrors(errors);
		return result;
	}

	public static ComplexResult getComplexSuccessResult() {
		ComplexResult result = new ComplexResult();
		result.setIsSuccess(true);
		return result;
	}
}
