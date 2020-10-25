package com.crm.service;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;
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
		return ticketRepository.findAll();
	}

	@Override
	public void saveTicket(Ticket ticket) {

		ticketRepository.save(ticket);

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

	@Override
	public void updateTicketGroup(String group,boolean isForwarded, Long id) {
	ticketRepository.updateTicketGroup(group,isForwarded,id);
		
	}

	@Override
	public List<Ticket> findTicketsByDevGroup() {
		return ticketRepository.findTicketsByDevGroup();
	}

	@Override
	public List<Ticket> findTicketsByMechGroup() {
		return ticketRepository.findTicketsByMechGroup();
	}

	@Override
	public List<Ticket> findTicketsByTestGroup() {
		return ticketRepository.findTicketsByTestGroup();
	}

	@Override
	public Optional<Ticket> findById(Long id) {
		return ticketRepository.findById(id);
	}

	@Override
	public void updateTicket(Ticket editedTicket, Ticket editTicket) {
		
		editedTicket.setTitle(editTicket.getTitle());
		editedTicket.setDeadline(editTicket.getDeadline());
		editedTicket.setStatus(editTicket.getStatus());
		editedTicket.setPriority(editTicket.getPriority());
		editedTicket.setDescription(editTicket.getDescription());
		editedTicket.setNotifier(editTicket.getNotifier());
		
		ticketRepository.save(editedTicket);
		
	}

	@Override
	public List<Object[]> modifiedUserAndDate(Long id) {
		return ticketRepository.modifiedUserAndDate(id);
	}

	
}
