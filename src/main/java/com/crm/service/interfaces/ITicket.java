package com.crm.service.interfaces;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.crm.model.Ticket;
import com.crm.model.User;

public interface ITicket {

	List<Ticket> findAll();
	
	public void saveTicket(Ticket client);
	
	public void updateTicket(String notifier,  String priority,  String title,String description,String status,Date date,User user,Long id);
	
	public void deleteById(Long id);
	
	public List<Object[]> ticketsGroupedByStatus();

	public  List<Object[]>  ticketsGroupedByPriority();
	
	public  List<Object[]> ticketGroupedByMonths();
}
