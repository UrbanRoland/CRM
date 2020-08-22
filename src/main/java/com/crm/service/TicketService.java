package com.crm.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crm.domain.Client;
import com.crm.domain.Ticket;
import com.crm.repository.TicketRepository;
import com.crm.service.interfaces.ITicket;

@Service
public class TicketService implements ITicket {

	
	private TicketRepository ticketRepository;
	
	@Autowired
	public TicketService(TicketRepository ticketRepository) {
		this.ticketRepository=ticketRepository;
	}
	
	
	@Override
	public List<Ticket> findAll() {
		return ticketRepository.findAll();
	}


	@Override
	public void saveTicket(Ticket ticket) {
	
		ticketRepository.save(ticket);
		
	}

}
