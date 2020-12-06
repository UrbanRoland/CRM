package com.crm.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.crm.model.Client;
import com.crm.model.Ticket;
import com.crm.model.User;
import com.crm.repository.TicketRepository;

@ExtendWith(MockitoExtension.class)
class TicketServiceImplTest {
	private final Logger logger = Logger.getLogger("TestLogger");
	@Mock
	private TicketRepository ticketRepository;

	@InjectMocks
	private TicketServiceImpl ticketServiceImpl;

	@BeforeEach
	public void init() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testfindById() {
		Ticket t = new Ticket();
		t.setId(1L);
		t.setTitle("Test");
		Mockito.when(ticketRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(t));

		Ticket ticket = ticketServiceImpl.findById(1L).get();
		assertThat(ticket.getId()).isEqualTo(1L);
	}

	@Test
	void testsaveTicket() {

		User user = new User();
		user.setId(1L);
		user.setEmail("urolir@gmail.com");

		Client client = new Client();
		client.setId(1L);
		client.setEmail("urolir@gmail2.com");

		Ticket t = new Ticket();
		t.setId(1L);
		t.setTitle("Test");

		Mockito.when(ticketRepository.save(Mockito.any(Ticket.class))).thenReturn(t);

		Ticket savedTicket = ticketServiceImpl.saveTicket(t, user, client);

		assertThat(savedTicket).isEqualToComparingFieldByField(t);
		Mockito.verify(ticketRepository).save(savedTicket);
	}

	@Test
	public void testfindAll() {

		List<Ticket> datas = new ArrayList<>();
		Ticket ticket = new Ticket();
		ticket.setId(1L);
		ticket.setTitle("TEST");

		Ticket ticket2 = new Ticket();
		ticket2.setId(1L);
		ticket2.setTitle("TEST2");

		datas.add(ticket);
		datas.add(ticket2);

		Mockito.when(ticketRepository.findAll()).thenReturn(datas);

		List<Ticket> expected = ticketServiceImpl.findAll();
		assertEquals(expected, datas);

	}

	@Test
	public void testdeleteById() {

		final Long ticketId = 1L;

		ticketServiceImpl.deleteById(ticketId);
		ticketServiceImpl.deleteById(ticketId);

		verify(ticketRepository, times(2)).deleteById(ticketId);
	}

	@Test
	public void testupdateTicket() {

		Ticket ticket = new Ticket();
		ticket.setId(1L);
		ticket.setTitle("TEST");

		Mockito.when(ticketRepository.save(Mockito.any(Ticket.class))).thenReturn(ticket);

		Ticket expected = new Ticket();
		expected = ticketServiceImpl.updateTicket(expected, ticket);

		assertThat(expected).isNotNull();
		assertEquals(expected.getTitle(), "TEST", "Not equals");
		verify(ticketRepository).save(Mockito.any(Ticket.class));

	}
	
	@Test
	public void testupdateTicketGroup() {
		String group="Tesztelő";
		Ticket ticket = new Ticket();
		ticket.setId(1L);
		ticket.setTitle("TEST");

		Mockito.when(ticketRepository.save(Mockito.any(Ticket.class))).thenReturn(ticket);

		Mockito.when(ticketRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(ticket));

		Ticket expected = ticketServiceImpl.findById(1L).get();
		
		expected=ticketServiceImpl.updateTicketGroup(1L, group);
		

		assertThat(expected).isNotNull();
		assertEquals(expected.getUserGroup(), "Tesztelő", "Not equals");
		assertEquals(expected.isForwarded(), true, "Not forwarded");
		verify(ticketRepository).save(Mockito.any(Ticket.class));
	}
	
	@Test
	public void testdeleteTicketByClient_ID() {

		final Long client_id = 1L;

		ticketServiceImpl.deleteTicketByClient_ID(client_id);
		ticketServiceImpl.deleteTicketByClient_ID(client_id);


		verify(ticketRepository, times(2)).deleteTicketByClient_ID(client_id);
	}
	

	

}
