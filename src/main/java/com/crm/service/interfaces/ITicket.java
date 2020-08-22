package com.crm.service.interfaces;

import java.util.List;


import com.crm.domain.Ticket;

public interface ITicket {

	List<Ticket> findAll();
	
	public void saveTicket(Ticket client);
}
