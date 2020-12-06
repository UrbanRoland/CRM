package com.crm.service;

import java.util.List;
import java.util.Optional;

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
		return (List<Ticket>) ticketRepository.findAll();
	}

	@Override
	public Ticket saveTicket(Ticket ticket, User u, Client client) {
		ticket.setUser(u);
		ticket.setStatus("Nyitott");
		ticket.setClient(client);
		return ticketRepository.save(ticket);
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
	public void deleteTicketByClient_ID(Long client_id) {
		ticketRepository.deleteTicketByClient_ID(client_id);
		
	}

	@Override
	public Ticket updateTicketGroup(Long id,String group) {
		Ticket ticket=ticketRepository.findById(id).get();
		ticket.setForwarded(true);
		ticket.setUserGroup(group);
		return ticketRepository.save(ticket);
		
	}

	@Override
	public Optional<Ticket> findById(Long id) {
		return ticketRepository.findById(id);
	}

	@Override
	public Ticket updateTicket(Ticket editedTicket, Ticket editTicket) {
		
		editedTicket.setTitle(editTicket.getTitle());
		editedTicket.setDeadline(editTicket.getDeadline());
		editedTicket.setStatus(editTicket.getStatus());
		editedTicket.setPriority(editTicket.getPriority());
		editedTicket.setDescription(editTicket.getDescription());
		editedTicket.setNotifier(editTicket.getNotifier());
		
		return ticketRepository.save(editedTicket);
		
	}

	
}
