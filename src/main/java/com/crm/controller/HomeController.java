package com.crm.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.crm.domain.User;
import com.crm.service.UserService;

@Controller
public class HomeController {
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	private UserService userService;
	
	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	@RequestMapping("/")
	public String slash(){
		return "main";
	}
	@RequestMapping("/login")
	public String login(){
		return "login";
	}
	
	@RequestMapping("registration")
	public String registration(Model model) {
	model.addAttribute("user",new User());
		return "registration";
	}
	@PostMapping("/reg")
	public String reg(@ModelAttribute User user){
	//	log.info("Uj user!");
//		emailService.sendMessage(user.getEmail());
		//log.debug(user.getUsername());
		//log.debug(user.getEmail());
		//log.debug(user.getPassword());
	
		userService.registerUser(user);
		return "auth/login";
	}
	
	@RequestMapping("forgetPassword")
	public String forgetPassword(){
		return "forgetPassword";
	}
	@RequestMapping("main")
	public String main(){
		return "main";
	}
	
	
}
