package com.crm.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.crm.domain.Ticket;

public interface TicketRepository extends CrudRepository<Ticket, Long> {

	List<Ticket> findAll();
	
}
