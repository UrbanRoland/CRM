package com.crm.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.crm.model.Ticket;

@Repository
public interface TicketRepository extends CrudRepository<Ticket, Long> {

	
	@Query(value = "SELECT Status , COUNT(id) FROM ticket GROUP BY Status", nativeQuery = true)
	 List<Object[]> ticketsGroupedByStatus();
	
	@Query(value = "SELECT Priority, COUNT(id) FROM ticket GROUP BY Priority", nativeQuery = true)
	 List<Object[]> ticketsGroupedByPriority();
	 
	 //2020-ban bejelentett hibak havi szinten lebontva
	 @Query(value="SELECT to_char(created_date, 'YYYY-MM'), COUNT(id)" + 
	 		"FROM ticket " + 
	 		"WHERE created_date>='2020-01-01' and " + 
	 		"created_date<='2020-12-31'" + 
	 		"GROUP BY to_char(created_date, 'YYYY-MM')",nativeQuery = true)
	 List<Object[]> ticketGroupedByMonths();
	 
		@Transactional
		@Modifying
		@Query(value="DELETE FROM ticket WHERE client_id= ?1",nativeQuery = true)
	 void deleteTicketByClient_ID(Long client_id);
	 
		@Transactional
		@Modifying
		@Query(value="UPDATE ticket SET user_group=?1, is_forwarded=?2 WHERE id= ?3",nativeQuery = true)
	 void updateTicketGroup(String group,boolean isForwarded,Long id);
		
}
