package com.crm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crm.model.Client;
import com.crm.repository.ClientRepository;
import com.crm.service.interfaces.IClient;

@Service
public class ClientServiceImpl implements IClient {

	private ClientRepository clientRepository;
	
	@Autowired
	public ClientServiceImpl(ClientRepository clientRepository) {
		this.clientRepository=clientRepository;
	}
	
	
	public void saveClient(Client client) {
		clientRepository.save(client);
		
	}

	@Override
	public List<Client> findAll() {
		return clientRepository.findAll();
	}


	@Override
	public void deleteById(Long id) {
		clientRepository.deleteById(id);
		
	}


	@Override
	public Client findById(Long id) {
		return clientRepository.findById(id).orElse(null);
	}

	@Override
	public void updateClient(Client editedClient, Client editClient) {
		
		editedClient.setName(editClient.getName());
		editedClient.setContactPerson(editClient.getContactPerson());
		editedClient.setEmail(editClient.getEmail());
		editedClient.setZipCode(editClient.getZipCode());
		editedClient.setPhone(editClient.getPhone());
		editedClient.setTaxnumber(editClient.getTaxnumber());
		editedClient.setCity(editClient.getCity());
		editedClient.setAddress(editClient.getAddress());
		editedClient.setMonthlyFee(editClient.getMonthlyFee());
		
		clientRepository.save(editedClient);
		
	}
}
