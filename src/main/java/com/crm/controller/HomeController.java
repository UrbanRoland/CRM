package com.crm.controller;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.crm.domain.User;
import com.crm.service.UserService;
import com.crm.validators.ForgetPassword;
import com.crm.validators.RegValidator;
import com.crm.validators.ResetPassword;

@Controller
public class HomeController {
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	private UserService userService;

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	@Autowired
	private PasswordEncoder passwordEncoder;

	@RequestMapping("/")
	public String slash() {
		return "main";
	}

	@RequestMapping("main")
	public String main() {
		return "main";
	}

	@RequestMapping("/login")
	public String login() {
		return "login";
	}

	@RequestMapping("registration")
	public String registration(Model model) {
		model.addAttribute("user", new User());

		return "registration";
	}

	@PostMapping("/reg")
	public String reg(@ModelAttribute @Validated User user, BindingResult bindingResult) {

		RegValidator validator = new RegValidator();

		for (int i = 0; i < userService.allEmail().size(); i++) {
			if (user.getEmail().equals(userService.allEmail().get(i))) {
				bindingResult.rejectValue("email", "valid.email", "Ez az email cím már foglalt!");
			}
		}

		validator.validate(user, bindingResult);
		if (bindingResult.hasErrors()) {
			return "registration";
		}
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		
		user.setPassword(encodedPassword);
		user.setPasswordConf(encodedPassword);
		
		userService.registerUser(user);
		return "redirect:/login?needActivation";
	}

	@RequestMapping("/forgetPassword")
	public String forgetPassword(Model model) {
		model.addAttribute("user", new User());
		return "forgetPassword";
	}

	@PostMapping("/forgetPasswordAction")
	public String forgetPasswordAction(@ModelAttribute @Validated User user, BindingResult bindingResult,
			@RequestParam String email) {
		
	

		String response = userService.forgotPassword(email);

		if (!response.startsWith("Invalid")) {

		
			return "redirect:/login?emailSend";
		
		} else {
			return "redirect:/forgetPassword?emailNotFound";
		}
	
	}

	@RequestMapping(value = "/confirm-reset")
	public String resetPassword(Model model, @RequestParam String token, @ModelAttribute  User user,BindingResult bindingResult,
			HttpSession session) {

	
		
		session.setAttribute("token", user.getToken());

		model.addAttribute("user", user);

		return "resetPassword";
	}

	@PostMapping(value = "/reset-password")
	public String resetPassword(@ModelAttribute @Validated User user, HttpSession session,BindingResult bindingResult) {
		String token = null;

		if (session.getAttribute("token") != null) {
			token = (String) session.getAttribute("token");

		}
		
		ResetPassword validator=new ResetPassword();
		validator.validate(user, bindingResult);
		if (bindingResult.hasErrors()) {
			return "resetPassword";
		}
		
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		
		if (userService.resetPassword(token, encodedPassword) == "Invalid token.") {
			return "redirect:/login?invalidToken";
		} else if (userService.resetPassword(token, encodedPassword) == "Token expired.") {
			return "redirect:/login?tokenExpired";
		} else {
			return "redirect:/login?passwordChanged";
		}
	
		
	
		

	}

	@RequestMapping(path = "/activation/{code}", method = RequestMethod.GET)
	public String activation(@PathVariable("code") String code, HttpServletResponse response) {
		userService.userActivation(code);
		return "redirect:/login?activationSuccess";
	}

}
