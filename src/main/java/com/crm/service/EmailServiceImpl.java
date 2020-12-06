package com.crm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.crm.service.interfaces.IEmail;

@Service
public class EmailServiceImpl implements IEmail {


	@Value("${spring.mail.username}")
	private String sender;

	@Value("${activation.link}")
	private String activationLink;
	
	@Value("${changed.link}")
	private String changedLink;
	
	private JavaMailSender javaMailSender;


	@Autowired
	public void setJavaMailSender(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}

	// Annak az email cimet varja parameterul aki beregisztralt tehat ez lesz a cimzett
	@Override
	public void successRegistration(String email, String key) {
		SimpleMailMessage message = null;
		try {

			message = new SimpleMailMessage();
	
			message.setFrom(sender);
	
			message.setTo(email);
		
			message.setSubject("Sikeres Regisztrálás");
			
			message.setText("Kedves " + email + "! \n \n Köszönjük, hogy regisztráltál az oldalunkra!" + "\n "+ "A fiókod aktiválására kattints az alábbi linkre: " +activationLink+key  );
			javaMailSender.send(message);
		} catch (Exception e) {
		System.out.println("Hiba az email küldésekor az alábbi címre: " + email + " " + e);
		}
	}
	
	@Override
	public void sendLinkToUser(String email,String link) {
		SimpleMailMessage message = null;
		try {

			message = new SimpleMailMessage();
			
			message.setFrom(sender);
			
			message.setTo(email);
			
			message.setSubject("Jelszó módosítás");
			
			message.setText("Kedves " + email + "! \n \n Az alábbi linken tudja megvátoztatni a jelszavát: " +changedLink+link+ "\n "  );
			javaMailSender.send(message);
		} catch (Exception e) {
			System.out.println("Hiba az email küldésekor az alábbi címre: " + email + " " + e);
		}
	}
	@Override
	public void changedPassword(String email) {
		SimpleMailMessage message = null;
		try {

			message = new SimpleMailMessage();
			
			message.setFrom(sender);
			
			message.setTo(email);
			
			message.setSubject("Jelszó módosítás");
			
			message.setText("Kedves " + email + "! \n \n A profiljához tartozó jelszó módisításra került!" + "\n "  );
			javaMailSender.send(message);
		} catch (Exception e) {
			System.out.println("Hiba az email küldésekor az alábbi címre: " + email + " " + e);
		}
	}

	
	
}
