package com.crm.service.interfaces;

public interface IEmail {

	public void successRegistration(String email, String key);
	public void sendLinkToUser(String email,String link);
	public void changedPassword(String email);
	
}
