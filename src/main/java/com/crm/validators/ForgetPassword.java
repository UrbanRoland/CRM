package com.crm.validators;

import java.util.regex.Pattern;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.crm.domain.User;

public class ForgetPassword implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return User.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "valid.email2", "Kötlező kitölteni!");
		User user = (User) target;

	

		if (matchEmail(user.getEmail()) == false) {
			errors.rejectValue("email", "valid.email", "Nem megfelelő az email formátuma!");
		}
	}

	public Boolean matchEmail(String content) {
		Pattern p = Pattern.compile("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$",
				Pattern.DOTALL | Pattern.CASE_INSENSITIVE);
		if (p.matcher(content).find()) {
			return true;
		} else {
			return false;
		}
	}

}
