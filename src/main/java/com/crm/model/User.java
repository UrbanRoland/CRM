package com.crm.model;

import java.io.File;
import java.lang.annotation.Documented;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table( name="users" )
public class User extends Audit<String> {

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Long id;
	
	@Column( unique=true, nullable=false )
	private String email;
	
	@Column( nullable=false )
	//@Size(min=5, message = "A jelszónak legalább 5 karaktert kell tartalmaznia!")
	private String password;
	
	private String passwordConf;
	
	@Column( nullable=false )
	private String username;
	
	private String activation;
	
	private Boolean enabled;
	
	private Boolean image;
	
	



	public Boolean getImage() {
		return image;
	}



	public void setImage(Boolean image) {
		this.image = image;
	}



	private String token;
	@Column(columnDefinition = "TIMESTAMP")
	private LocalDateTime tokenCreationDate;
	
	@ManyToMany( cascade = CascadeType.ALL, fetch = FetchType.EAGER )
	@JoinTable( 
		name = "users_roles", 
		joinColumns = {@JoinColumn(name="user_id")}, 
		inverseJoinColumns = {@JoinColumn(name="role_id")}  
	)
	private Set<Role> roles = new HashSet<Role>();
	
	@OneToMany(mappedBy = "user")
	private List<Ticket> tickets;
	
	
	public User() {}
	
	

	public List<Ticket> getTickets() {
		return tickets;
	}



	public void setTickets(List<Ticket> tickets) {
		this.tickets = tickets;
	}



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}



	public String getPassword() {
		return password;
	}



	public void setPassword(String password) {
		this.password = password;
	}



	public String getUsername() {
		return username;
	}



	public void setUsername(String username) {
		this.username = username;
	}



	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	
	public void addRoles(String roleName) {
		if (this.roles == null || this.roles.isEmpty()) 
			this.roles = new HashSet<>();
		this.roles.add(new Role(roleName));
	}







	public String getPasswordConf() {
		return passwordConf;
	}



	public void setPasswordConf(String passwordConf) {
		this.passwordConf = passwordConf;
	}



	public String getActivation() {
		return activation;
	}



	public void setActivation(String activation) {
		this.activation = activation;
	}



	public Boolean getEnabled() {
		return enabled;
	}



	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}



	public String getToken() {
		return token;
	}



	public void setToken(String token) {
		this.token = token;
	}
	
	public LocalDateTime getTokenCreationDate() {
		return tokenCreationDate;
	}



	public void setTokenCreationDate(LocalDateTime date) {
		this.tokenCreationDate = date;
	}



	@Override
	public String toString() {
		return "User [id=" + id + ", email=" + email + ", password=" + password + ", passwordConf=" + passwordConf
				+ ", username=" + username + ", activation=" + activation + ", enabled=" + enabled + ", image=" + image
				+ ", token=" + token + ", tokenCreationDate=" + tokenCreationDate + ", roles=" + roles + ", tickets="
				+ tickets + "]";
	}
	
	
	
}