package com.crm.domain;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table( name="users" )
public class User {

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
	
	public User() {}
	
	

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

	@Override
	public String toString() {
		return "User [id=" + id + ", email=" + email + ", password=" + password + "]";
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
	
	
	
}