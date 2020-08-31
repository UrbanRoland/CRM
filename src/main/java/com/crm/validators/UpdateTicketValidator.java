package com.crm.validators;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.crm.domain.Client;
import com.crm.domain.Ticket;

public class UpdateTicketValidator  implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Ticket.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "notifier", "valid.notifier","Kötlező kitölteni!");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "valid.title", "Kötlező kitölteni!");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "valid.description", "Kötlező kitölteni!");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "deadline", "valid.deadline", "Kötlező kitölteni!");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "priority", "valid.priority", "Kötlező kitölteni!");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "status", "valid.status", "Kötlező kitölteni!");
		
		
	}

}
