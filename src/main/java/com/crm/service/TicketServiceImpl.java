package com.crm.service;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crm.model.Client;
import com.crm.model.Ticket;
import com.crm.model.User;
import com.crm.repository.TicketRepository;
import com.crm.service.interfaces.ITicket;

@Service
public class TicketServiceImpl implements ITicket {

	private TicketRepository ticketRepository;

	@Autowired
	public TicketServiceImpl(TicketRepository ticketRepository) {
		this.ticketRepository = ticketRepository;
	}

	@Override
	public List<Ticket> findAll() {
		return ticketRepository.findAll();
	}

	@Override
	public void saveTicket(Ticket ticket) {

		ticketRepository.save(ticket);

	}

	@Override
	public void updateTicket(String notifier, String priority, String title, String description, String status,
			Date deadline, User user, Long id) {
		ticketRepository.updateTicket(notifier, priority, title, description, status, deadline, user, id);

	}

	@Override
	public void deleteById(Long id) {
		ticketRepository.deleteById(id);

	}

	@Override
	public List<Object[]> ticketsGroupedByPriority() {
		List<Object[]> list = ticketRepository.ticketsGroupedByPriority();

		return list;
	}

	@Override
	public List<Object[]> ticketsGroupedByStatus() {
		List<Object[]> list = ticketRepository.ticketsGroupedByStatus();
		return list;
	}

	@Override
	public List<Object[]> ticketGroupedByMonths() {
		List<Object[]> list = ticketRepository.ticketGroupedByMonths();
		return list;
	}

	@Override
	public List<Ticket> findTicketByClient_ID(Long client_id) {
		return ticketRepository.findTicketByClient_ID(client_id);
	}

	@Override
	public void deleteTicketByClient_ID(Long client_id) {
		ticketRepository.deleteTicketByClient_ID(client_id);
		
	}

}