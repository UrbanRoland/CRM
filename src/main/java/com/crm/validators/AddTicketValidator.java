package com.crm.validators;

import java.util.Date;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.crm.model.Client;
import com.crm.model.Ticket;

@Component
public class AddTicketValidator  implements Validator{

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
	
		Ticket ticket = (Ticket) target;
		Date currentDate = new Date(System.currentTimeMillis());
		
		if (ticket.getDeadline() != null) {
			if (ticket.getDeadline().before(currentDate)) {
				errors.rejectValue("deadline", "deadlineMustOlderThanCurrentDate", "A megadott dátum nem érvényes!");
			}
		}
	}

}
