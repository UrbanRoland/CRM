package com.crm.repository;

import java.util.Date;
import java.util.List;


import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.crm.model.Ticket;
import com.crm.model.User;

public interface TicketRepository extends CrudRepository<Ticket, Long> {

	List<Ticket> findAll();
	
	@Transactional
	@Modifying
	@Query(value="UPDATE ticket SET notifier =  ?1, priority = ?2, title = ?3, description = ?4,  status = ?5, deadline= ?6, user_id= ?7  WHERE id = ?8",nativeQuery = true)
	void updateTicket( String notifier,  String priority,  String title,String description, String status,Date deadline,User user,Long id);
	
	@Query(value = "SELECT Status , COUNT(id) FROM ticket GROUP BY Status", nativeQuery = true)
	 List<Object[]> ticketsGroupedByStatus();
	
	@Query(value = "SELECT Priority, COUNT(id) FROM ticket GROUP BY Priority", nativeQuery = true)
	 List<Object[]> ticketsGroupedByPriority();
	 
	 //2020-ban bejelentett hibak havi szinten lebontva
	 @Query(value="SELECT to_char(creation_date, 'YYYY-MM'), COUNT(id)" + 
	 		"FROM ticket " + 
	 		"WHERE creation_date>='2020-01-01' and " + 
	 		"creation_date<='2020-12-31'" + 
	 		"GROUP BY to_char(creation_date, 'YYYY-MM')",nativeQuery = true)
	 List<Object[]> ticketGroupedByMonths();
	 
}
