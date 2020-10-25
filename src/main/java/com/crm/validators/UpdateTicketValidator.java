package com.crm.validators;

import java.util.Date;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.crm.model.Client;
import com.crm.model.Ticket;

@Component
public class UpdateTicketValidator  implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Ticket.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "notifier", "notifierCannotBeEmpty","Kötlező kitölteni!");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "titleCannotBeEmpty", "Kötlező kitölteni!");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "descriptionCannotBeEmpt", "Kötlező kitölteni!");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "deadline", "deadlineCannotBeEmpt", "Kötlező kitölteni!");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "priority", "priorityCannotBeEmpt", "Kötlező kitölteni!");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "status", "statusCannotBeEmpt", "Kötlező kitölteni!");
	
		
		Ticket ticket = (Ticket) target;
		Date currentDate = new Date(System.currentTimeMillis());
		
		if(ticket.getDeadline()!=null) {
			if (ticket.getDeadline().before(currentDate)) {
				errors.rejectValue("deadline", "deadlineMustOlderThanCurrentDate", "Régebbi dátum kell, hogy legyen mint a jelenlegi!");	
			}
			
		}
	
	}

}
