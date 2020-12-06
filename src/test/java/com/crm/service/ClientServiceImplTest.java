package com.crm.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
import com.crm.repository.ClientRepository;

@ExtendWith(MockitoExtension.class)
class ClientServiceImplTest {
	private final Logger logger = Logger.getLogger("TestLogger");

	@Mock
	private ClientRepository clientRepository;

	@InjectMocks
	private ClientServiceImpl clientServiceImpl;

	@BeforeEach
	public void init() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testsaveClient() {

		Client c = new Client();
		c.setId(1L);
		c.setEmail("urolir@gmail.com");

		Mockito.when(clientRepository.save(Mockito.any(Client.class))).thenReturn(c);

		Client u = clientServiceImpl.saveClient(c);

		assertThat(u).isEqualToComparingFieldByField(c);

		Mockito.verify(clientRepository).save(u);
	}

	@Test
	public void testfindById() {

		Client c = new Client();
		c.setId(1L);
		c.setEmail("urolir@gmail.com");
		Mockito.when(clientRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(c));

		Client client = clientServiceImpl.findById(1L);
		assertThat(client.getId()).isEqualTo(1L);
	}

	@Test
	public void testfindAll() {

		List<Client> datas = new ArrayList<>();
		Client c1 = new Client();
		c1.setId(1L);
		c1.setEmail("urolir@gmail.com");

		Client c2 = new Client();
		c2.setId(2L);
		c2.setEmail("urolir@gmail.comm");

		datas.add(c1);
		datas.add(c2);

		Mockito.when(clientRepository.findAll()).thenReturn(datas);

		List<Client> expected = clientServiceImpl.findAll();
		assertEquals(expected, datas);

	}

	@Test
	public void testdeleteById() {
		final Long clientId = 1L;

		clientServiceImpl.deleteById(clientId);
		clientServiceImpl.deleteById(clientId);

		verify(clientRepository, times(2)).deleteById(clientId);
	}

	@Test
	public void testupdateClient() {
		Client client = new Client();
		client.setId(1L);
		client.setEmail("urolir@gmail.com");

		Mockito.when(clientRepository.save(Mockito.any(Client.class))).thenReturn(client);

		Client expected = new Client();
		expected = clientServiceImpl.updateClient(expected, client);

		assertThat(expected).isNotNull();
		assertEquals(expected.getEmail(), "urolir@gmail.com", "Not equals");
		verify(clientRepository).save(Mockito.any(Client.class));
	}

}
