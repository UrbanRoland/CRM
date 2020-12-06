package com.crm.service.interfaces;

import java.util.List;
import java.util.Optional;

import com.crm.model.Client;
import com.crm.model.Ticket;
import com.crm.model.User;

public interface ITicket {

	List<Ticket> findAll();
	
	public Ticket saveTicket(Ticket ticket,User u,Client client);
	
	public Ticket updateTicket(Ticket editedTicket, Ticket editTicket);
	
	public void deleteById(Long id);
	
	public List<Object[]> ticketsGroupedByStatus();

	public  List<Object[]>  ticketsGroupedByPriority();
	
	public  List<Object[]> ticketGroupedByMonths();
	
	public  void deleteTicketByClient_ID(Long client_id);
	
	public Ticket updateTicketGroup(Long id,String group);
	
	public Optional<Ticket> findById(Long id);
	
}
