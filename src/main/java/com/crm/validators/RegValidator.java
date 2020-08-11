package com.crm.validators;

import java.util.regex.Pattern;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.crm.domain.User;

public class RegValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return User.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "valid.username", "Kötlező kitölteni!");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "valid.email2", "Kötlező kitölteni!");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "valid.password", "Kötlező kitölteni!");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "passwordConf", "valid.passwordConf", "Kötlező kitölteni!");


		User user = (User) target;

		
		if (user.getPassword().length()<5) {
			errors.rejectValue("password", "valid.password", "A jelszónak legalább 5 karaktert kell tartalmaznia!");
		}
		
		if (!user.getPassword().equals(user.getPasswordConf())) {
			errors.rejectValue("passwordConf", "valid.passwordConfDiff", "A két jelszó nem egyezzik meg!");
		}

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
