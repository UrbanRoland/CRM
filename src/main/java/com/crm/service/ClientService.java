package com.crm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crm.domain.Client;
import com.crm.repository.ClientRepository;
import com.crm.service.interfaces.IClient;

@Service
public class ClientService implements IClient {

	
	private ClientRepository clientRepository;
	
	@Autowired
	public ClientService(ClientRepository clientRepository) {
		this.clientRepository=clientRepository;
	}
	
	
	public void saveClient(Client client) {
		clientRepository.save(client);
		
	}


	@Override
	public List<Client> findAll() {
		return clientRepository.findAll();
	}
}
