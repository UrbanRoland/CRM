package com.crm.validators;

import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.crm.model.User;
import com.crm.service.UserServiceImpl;

@Component
public class UserRegistrationValidator implements Validator {

	private UserServiceImpl userService;	
	
	@Autowired
	public void setUserService(UserServiceImpl userService) {
		this.userService = userService;
	}
	
	
	@Override
	public boolean supports(Class<?> clazz) {
		return User.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "usernameCannotBeEmpty", "Kötlező kitölteni!");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "emailCannotBeEmpty", "Kötlező kitölteni!");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "passwordCannotBeEmpty", "Kötlező kitölteni!");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "passwordConf", "passwordConfCannotBeEmpty", "Kötlező kitölteni!");


		User user = (User) target;

		if (user.getUsername().length()<5) {
			errors.rejectValue("username", "usernameMustBeLongerThan5Characters", "A felhasználónévnek legalább 5 karaktert kell tartalmaznia!");
		}
		
		for(User u:userService.findAll()) {
			if(u.getEmail().equals(user.getEmail())) {
				errors.rejectValue("email", "emailAlreadyExists", "Ez az email már foglalt!");
			}
		}
		
		
		if (user.getPassword().length()<5) {
			errors.rejectValue("password", "passwordMustBeLongerThan5Characters", "A jelszónak legalább 5 karaktert kell tartalmaznia!");
		}
		
		if (!user.getPassword().equals(user.getPasswordConf())) {
			errors.rejectValue("password", "passwordsDoNotMatch", "A két jelszó nem egyezzik meg!");
			errors.rejectValue("passwordConf", "passwordsDoNotMatch", "A két jelszó nem egyezzik meg!");
		}

		if (matchEmail(user.getEmail()) == false) {
			errors.rejectValue("email", "emailIsNotValid", "Nem megfelelő az email formátuma!");
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
