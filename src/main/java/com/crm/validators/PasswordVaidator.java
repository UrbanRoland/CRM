package com.crm.validators;

import java.util.regex.Pattern;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.crm.model.User;

@Component
public class PasswordVaidator implements Validator{
	
	@Override
	public boolean supports(Class<?> clazz) {
		return User.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "passwordCannotBeEmpty", "Kötlező kitölteni!");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "passwordConf", "passwordConfCannotBeEmpty", "Kötlező kitölteni!");

		User user = (User) target;
	
		
		if (user.getPassword().length()<5) {
			errors.rejectValue("password", "valid.password", "A jelszónak legalább 5 karaktert kell tartalmaznia!");
		}
		
		if (!user.getPassword().equals(user.getPasswordConf())) {
			errors.rejectValue("password", "passwordsDoNotMatch", "A két jelszó nem egyezzik meg!");
			errors.rejectValue("passwordConf", "passwordsDoNotMatch", "A két jelszó nem egyezzik meg!");
		}
	


	}

}
