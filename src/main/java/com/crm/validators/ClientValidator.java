package com.crm.validators;

import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.crm.model.Client;
import com.crm.service.ClientServiceImpl;


@Component
public class ClientValidator  implements Validator {

	private ClientServiceImpl clientServiceImpl;	
	
	
	@Autowired
	public void setClientServiceImpl(ClientServiceImpl clientServiceImpl) {
		this.clientServiceImpl = clientServiceImpl;
	}

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
		
		
		
		for(Client c:clientServiceImpl.findAll()) {
			if(c.getEmail().equals(client.getEmail())) {
				errors.rejectValue("email", "emailAlreadyExists", "Ez az email már foglalt!");
			}
		}	
	}

	//segedfuggveny
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
