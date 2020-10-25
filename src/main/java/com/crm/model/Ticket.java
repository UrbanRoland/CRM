package com.crm.model;

import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.springframework.format.annotation.DateTimeFormat;


@Entity
public class Ticket  extends Audit<String>{
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Long id;
	
	@ManyToOne
	private Client client;

	private String notifier;
	
	private String priority;

	private String title;
	
	private String description;
	
	private Date deadline;
	
	private String status;
	
	
	
	@ManyToOne
	private User user;

	private String userGroup;
	
	private boolean isForwarded; 
	
	public String getUserGroup() {
		return userGroup;
	}

	public void setUserGroup(String userGroup) {
		this.userGroup = userGroup;
	}

	public boolean isForwarded() {
		return isForwarded;
	}

	public void setForwarded(boolean isForwarded) {
		this.isForwarded = isForwarded;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public String getNotifier() {
		return notifier;
	}

	public void setNotifier(String notifier) {
		this.notifier = notifier;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDeadline() {
		return deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Ticket [id=" + id + ", client=" + client + ", notifier=" + notifier + ", priority=" + priority
				+ ", title=" + title + ", description=" + description + ", deadline=" + deadline + ", status=" + status
				+ ", user=" + user + ", userGroup=" + userGroup + ", isForwarded=" + isForwarded + "]";
	}



}
