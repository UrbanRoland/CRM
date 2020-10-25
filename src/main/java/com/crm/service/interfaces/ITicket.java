package com.crm.service.interfaces;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.crm.model.Ticket;
import com.crm.model.User;

public interface ITicket {

	List<Ticket> findAll();
	
	public void saveTicket(Ticket client);
	
	public void updateTicket(Ticket editedTicket, Ticket editTicket);
	
	public void deleteById(Long id);
	
	public List<Object[]> ticketsGroupedByStatus();

	public  List<Object[]>  ticketsGroupedByPriority();
	
	public  List<Object[]> ticketGroupedByMonths();
	
	public List<Ticket> findTicketByClient_ID(Long client_id);
	
	public  void deleteTicketByClient_ID(Long client_id);
	
	public  void updateTicketGroup(String group,boolean isForwarded,Long id);
	
	public List<Ticket> findTicketsByDevGroup();
	
	public List<Ticket> findTicketsByMechGroup();
	
	public List<Ticket> findTicketsByTestGroup();
	
	public Optional<Ticket> findById(Long id);
	
	public List<Object[]> modifiedUserAndDate(Long id);
}
