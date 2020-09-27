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
	public void updateClient(String address, String city, String contactPerson, String email, Long monthlyFee,
			String name, String phone, Long taxnumber, String zipCode, Long id) {
		clientRepository.updateClient(address, city, contactPerson, email, monthlyFee, name, phone, taxnumber, zipCode, id);
		
	}


	@Override
	public void deleteById(Long id) {
		clientRepository.deleteById(id);
		
	}
}
