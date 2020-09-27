package com.crm.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
public class Client {

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Long id;
	
	@OneToMany(mappedBy = "client")
	private List<Ticket> tickets;
	
	
	private String name;
	
	private String contactPerson;
	
    private String city;

    private String zipCode;
    
    private String address;
	
	private String email;
	
	private Long taxnumber;
	
	private String phone;
	
	private Long monthlyFee;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Ticket> getTickets() {
		return tickets;
	}

	public void setTickets(List<Ticket> tickets) {
		this.tickets = tickets;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}



	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getTaxnumber() {
		return taxnumber;
	}

	public void setTaxnumber(Long taxnumber) {
		this.taxnumber = taxnumber;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Long getMonthlyFee() {
		return monthlyFee;
	}

	public void setMonthlyFee(Long monthlyFee) {
		this.monthlyFee = monthlyFee;
	}

	@Override
	public String toString() {
		return "Client [id=" + id + ", tickets=" + tickets + ", name=" + name + ", contactPerson=" + contactPerson
				+ ", city=" + city + ", zipCode=" + zipCode + ", address=" + address + ", email=" + email
				+ ", taxnumber=" + taxnumber + ", phone=" + phone + ", monthlyFee=" + monthlyFee + "]";
	}
	
	
	
}
