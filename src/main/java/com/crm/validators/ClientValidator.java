package com.crm.validators;

import java.util.regex.Pattern;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.crm.model.Client;


@Component
public class ClientValidator  implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Client.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "nameCannotBeEmpty", "Kötlező kitölteni!");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "emailCannotBeEmpty", "Kötlező kitölteni!");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "contactPerson", "contactPersonCannotBeEmpty", "Kötlező kitölteni!");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "zipCode", "zipCodeCannotBeEmpty", "Kötlező kitölteni!");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "city", "cityCannotBeEmpty", "Kötlező kitölteni!");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address", "addressCannotBeEmpty", "Kötlező kitölteni!");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "taxnumber", "taxnumberCannotBeEmpty", "Kötlező kitölteni!");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "phone", "phoneCannotBeEmpty", "Kötlező kitölteni!");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "monthlyFee", "monthlyFeeCannotBeEmpty", "Kötlező kitölteni!");
		
		
		Client client = (Client) target;
	
		
		
		if (matchEmail(client.getEmail()) == false) {
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
