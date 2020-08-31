package com.crm.repository;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.crm.domain.Ticket;
import com.crm.domain.User;

public interface TicketRepository extends CrudRepository<Ticket, Long> {

	List<Ticket> findAll();
	
	@Transactional
	@Modifying
	@Query(value="UPDATE ticket SET notifier =  ?1, priority = ?2, title = ?3, description = ?4,  status = ?5, deadline= ?6, user_id= ?7  WHERE id = ?8",nativeQuery = true)
	void updateTicket( String notifier,  String priority,  String title,String description, String status,Date deadline,User user,Long id);
	
}
