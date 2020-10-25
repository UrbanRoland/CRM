package com.crm.model;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.crm.service.UserDetailsImpl;



public class AuditImpl implements AuditorAware<String> {

	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

	public Optional<String> getCurrentAuditor() {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication == null || !authentication.isAuthenticated()
				|| authentication.getName().equals("anonymousUser")) {
			return Optional.of("Regisztációkor");

		} else {

			return Optional.ofNullable(((UserDetailsImpl) authentication.getPrincipal()).getName());
		}
	}


}
