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
	
	@Query(value = "SELECT last_modified_by, last_modified_date FROM ticket where id=?1", nativeQuery = true)
	 List<Object[]> modifiedUserAndDate(Long id);

	@Query(value = "SELECT LAST_MODIFIED_BY  FROM ticket where id=?1", nativeQuery = true)
	String findByCreationDate(Long id);
	
	@Transactional
	@Modifying
	@Query(value="UPDATE ticket SET notifier =  ?1, priority = ?2, title = ?3, description = ?4,  status = ?5, deadline= ?6, user_id= ?7  WHERE id = ?8",nativeQuery = true)
	void updateTicket( String notifier,  String priority,  String title,String description, String status,Date deadline,User user,Long id);
	
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
	 
	 @Query(value="SELECT title,status, deadline FROM ticket WHERE CLIENT_ID= ?1", nativeQuery = true)
	 List<Ticket> findTicketByClient_ID(Long client_id);
	 
		@Transactional
		@Modifying
		@Query(value="DELETE FROM ticket WHERE client_id= ?1",nativeQuery = true)
	 void deleteTicketByClient_ID(Long client_id);
	 
		@Transactional
		@Modifying
		@Query(value="UPDATE ticket SET user_group=?1, is_forwarded=?2 WHERE id= ?3",nativeQuery = true)
	 void updateTicketGroup(String group,boolean isForwarded,Long id);
		
		 	
		 @Query(value="SELECT * FROM ticket WHERE is_forwarded=true AND user_group='Fejlesztő'", nativeQuery = true)
		 List<Ticket> findTicketsByDevGroup();
		 
		 @Query(value="SELECT * FROM ticket WHERE is_forwarded=true AND user_group='Szerelő'", nativeQuery = true)
		 List<Ticket> findTicketsByMechGroup();
		 
		 @Query(value="SELECT * FROM ticket WHERE is_forwarded=true AND user_group='Tesztelő'", nativeQuery = true)
		 List<Ticket> findTicketsByTestGroup();
}
