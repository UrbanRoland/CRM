package com.crm.controller;

import java.math.BigInteger;
import java.text.Normalizer;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.crm.model.Client;
import com.crm.model.Ticket;
import com.crm.model.User;
import com.crm.service.ClientServiceImpl;
import com.crm.service.TicketServiceImpl;
import com.crm.service.UserDetailsImpl;
import com.crm.service.interfaces.IUser;
import com.crm.validators.AddTicketValidator;
import com.crm.validators.ClientValidator;
import com.crm.validators.RegValidator;
import com.crm.validators.ResetPassword;
import com.crm.validators.UpdateTicketValidator;
import com.crm.validators.UserNameValidator;

@Controller
public class HomeController {
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	private IUser userService;

	@Autowired
	private ClientServiceImpl clientService;

	@Autowired
	private TicketServiceImpl ticketService;

	@Autowired
	public void setUserService(IUser userService) {
		this.userService = userService;
	}

	@Autowired
	private PasswordEncoder passwordEncoder;

	@RequestMapping("addTicket")
	public String addTicket(Model model) {
		model.addAttribute("ticket", new Ticket());

		List<Client> allclient = clientService.findAll();
		model.addAttribute("client", allclient);
		return "addTicket";
	}

	@PostMapping("/addTicketToDatabase")
	public String addTicketToDatabase(@ModelAttribute("ticket") @Validated Ticket ticket, BindingResult bindingResult,
			Client client) {

		AddTicketValidator validator = new AddTicketValidator();

		if (ticket.getDeadline() == null) {
			// bindingResult.rejectValue("deadline", "valid.deadline", "Kötelező
			// kitölteni!");
		}

		validator.validate(ticket, bindingResult);
		if (bindingResult.hasErrors()) {
			return "addTicket";
		}

		ticket.setCreationDate(LocalDateTime.now());
		ticket.setStatus("Nyitott");

//System.out.println("A státusz " +ticket.getStatus());
		ticket.setClient(client);

		// megkeresni a bejelentkezett felhasznalo id
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Long authenticatedUserId = null;
		if (principal instanceof UserDetails) {
			authenticatedUserId = ((UserDetailsImpl) principal).getId();
		} else {
			authenticatedUserId = (Long) principal;
		}

		User user = userService.findById(authenticatedUserId).get();
		ticket.setUser(user);

		ticketService.saveTicket(ticket);

		return "redirect:/addTicket?ticketAddSuccess";
	}

	@RequestMapping(value = { "listTicket", "/" })
	public String listTicket(Model model) {
		model.addAttribute("tickets", ticketService.findAll());
		model.addAttribute("ticket", new Ticket());
		return "listTicket";
	}

	@PostMapping("/deleteTicket")
	public String deleteTicket(@ModelAttribute("ticket") Ticket ticket) {
		ticketService.deleteById(ticket.getId());
		return "redirect:/listTicket?ticketDeleteSuccess";
	}

	@PostMapping("/updateTicket")
	public String updateTicket(@ModelAttribute("ticket") @Validated Ticket ticket, BindingResult bindingResult) {

		Date date = ticket.getDeadline();

		if (date == null) {

			return "redirect:/listTicket?updateError";
		}

		//SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Long authenticatedUserId = null;
		if (principal instanceof UserDetails) {
			authenticatedUserId = ((UserDetailsImpl) principal).getId();
		} else {
			authenticatedUserId = (Long) principal;
		}

		User user = userService.findById(authenticatedUserId).get();

		UpdateTicketValidator validator = new UpdateTicketValidator();

		validator.validate(ticket, bindingResult);
		if (bindingResult.hasErrors()) {

			return "redirect:/listTicket?updateError";
		}

		ticketService.updateTicket(ticket.getNotifier(), ticket.getPriority(), ticket.getTitle(),
				ticket.getDescription(), ticket.getStatus(), ticket.getDeadline(), user, ticket.getId());
		return "redirect:/listTicket?ticketUpdateSuccess";
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
	public String resetPassword(Model model, @RequestParam String token, @ModelAttribute User user,
			BindingResult bindingResult, HttpSession session) {

		session.setAttribute("token", user.getToken());

		model.addAttribute("user", user);

		return "resetPassword";
	}

	@PostMapping(value = "/reset-password")
	public String resetPassword(@ModelAttribute @Validated User user, HttpSession session,
			BindingResult bindingResult) {
		String token = null;

		if (session.getAttribute("token") != null) {
			token = (String) session.getAttribute("token");

		}

		ResetPassword validator = new ResetPassword();
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

	@RequestMapping("settings")
	public String settings(Model model) {
		model.addAttribute("user", new User());
		return "settings";
	}

	@PostMapping("/updateUserName")
	public String updateUserName(@ModelAttribute @Validated User user, BindingResult bindingResult) {

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String authenticatedUserEmail = null;
		if (principal instanceof UserDetails) {
			authenticatedUserEmail = ((UserDetailsImpl) principal).getUserEmail();
		} else {
			authenticatedUserEmail = principal.toString();
		}

		UserNameValidator validator = new UserNameValidator();
		validator.validate(user, bindingResult);
		if (bindingResult.hasErrors()) {
			return "settings";
		}

		userService.updateUserName(user.getUsername(), authenticatedUserEmail);

		return "redirect:/settings?updateUserNameSuccess";
	}

	@PostMapping("/updateUserPassword")
	public String updateUserPassword(@ModelAttribute @Validated User user, BindingResult bindingResult) {

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String authenticatedUserEmail = null;
		if (principal instanceof UserDetails) {
			authenticatedUserEmail = ((UserDetailsImpl) principal).getUserEmail();
		} else {
			authenticatedUserEmail = principal.toString();
		}

		ResetPassword validator = new ResetPassword();

		validator.validate(user, bindingResult);
		if (bindingResult.hasErrors()) {
			return "settings";
		}

		String encodedPassword = passwordEncoder.encode(user.getPassword());

		userService.updateUserPassword(encodedPassword, encodedPassword, authenticatedUserEmail);

		return "redirect:/settings?updatedUserPasswordSuccess";
	}

	@RequestMapping("addClient")
	public String addClient(Model model) {
		model.addAttribute("client", new Client());
		return "addClient";
	}

	@PostMapping("/addToClient")
	public String addToClient(@ModelAttribute @Validated Client client, BindingResult bindingResult) {

		ClientValidator validator = new ClientValidator();
		validator.validate(client, bindingResult);
		if (bindingResult.hasErrors()) {
			return "addClient";
		}

		clientService.saveClient(client);
		return "redirect:/addClient?clientAddSuccess";
	}

	@RequestMapping("uploadImage")
	public String uploadImage() {
		return "uploadImage";
	}

	@PostMapping("/upload")
	public String uploadFile(@RequestParam("file") MultipartFile file, RedirectAttributes attributes) {
		//String result = "";
		System.out.println("A kép neve: " + file.getOriginalFilename());
		System.out.println("A kép forrása: " + file.toString());

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Long authenticatedUserId = null;
		if (principal instanceof UserDetails) {
			authenticatedUserId = ((UserDetailsImpl) principal).getId();
		} else {
			authenticatedUserId = (Long) principal;
		}

		User user = userService.findById(authenticatedUserId).get();

		System.out.println(user.toString());

		try {
			userService.addPhoto(file, user);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("hiba", e);

		}

		return "redirect:/";
	}

	@RequestMapping("listUsers")
	public String listUsers(Model model) {
		model.addAttribute("users", userService.findAll());
		model.addAttribute("user", new User());
		return "listUsers";
	}
	
	
	@PostMapping("/updateUserRole")
	public String updateUseerRole(@ModelAttribute("user") @Validated User user, BindingResult bindingResult) {
		
		UserNameValidator validator = new UserNameValidator();
		Long user_id=user.getId();
		
		validator.validate(user, bindingResult);
		if (bindingResult.hasErrors()) {
			return "redirect:/listUsers?updateRoleError";
		}
		
		// a username valtozoba beletoltom a role-t
		Long role_id=userService.findByRoleName(user.getUsername());
		userService.updateUserRole(user_id, role_id);
		return "listUsers";
	}
	
	@PostMapping("/deleteUser")
	public String deleteUser(@ModelAttribute("user") User user) {
		userService.deleteUsers_Roles(user.getId());
		userService.deleteById(user.getId());
		System.out.println("A user azonositoja:"+ user.getId());
		return "redirect:/listUsers?userDeleteSuccess";
	}
	
	//lecsereli az ekezetes karakterekete
	public String stripAccents(String s){
	    s = Normalizer.normalize(s, Normalizer.Form.NFD);
	    s = s.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
	    return s;
	}
	
	@GetMapping("/displayPieCharts")
	public String pieCharts(Model model) {
		
	//adatok prioritas alapjan
	  for (Object[] ob : ticketService.ticketsGroupedByPriority()){
	        String key = (String)ob[0];
	        Integer value = ((BigInteger)ob[1]).intValue();
	    	model.addAttribute(stripAccents(key.replaceAll("\\s+","")), value);
	    }
	  //adatok statusz alapjan
	  for (Object[] ob : ticketService.ticketsGroupedByStatus()){
	        String key = (String)ob[0];
	        Integer value = ((BigInteger)ob[1]).intValue();
	    	model.addAttribute(stripAccents(key), value);
	    }

	
		return "pieCharts";
	}
	
	@GetMapping("/displayLineCharts")
	public String lineCharts(Model model) {
		
		  for (Object[] ob : ticketService.ticketGroupedByMonths()){
		        String key = (String)ob[0];
		        
		        switch (key) {
				case "2020-01": key="jan"; break;
				case "2020-02": key="feb"; break;		
				case "2020-03": key="mar"; break;			
				case "2020-04":	key="apr"; break;
				case "2020-05":	key="may"; break;				
				case "2020-06": key="jun"; break;				
				case "2020-07":	key="jul"; break;							
				case "2020-08": key="aug"; break;								
				case "2020-09": key="sep"; break;								
				case "2020-10": key="oct"; break;								
				case "2020-11": key="nov"; break;								
				case "2020-12": key="dec"; break;
				}
		        
		        Integer value = ((BigInteger)ob[1]).intValue();
		        model.addAttribute(key, value);
		    
		    }
		 
		return "lineCharts";
	}
	@RequestMapping("listClient")
	public String listClien(Model model) {
		model.addAttribute("clients", clientService.findAll());
		model.addAttribute("client", new Client());
		
		return "listClient";
	}
	@PostMapping("/updateClient")
	public String updateClient(@ModelAttribute("client") @Validated Client client, BindingResult bindingResult) {

		ClientValidator validator = new ClientValidator();

		validator.validate(client, bindingResult);
		if (bindingResult.hasErrors()) {

			return "redirect:/listClient?updateError";
		}

		clientService.updateClient(client.getAddress(), client.getCity(), client.getContactPerson(), client.getEmail(),  client.getMonthlyFee(), client.getName(), client.getPhone(), client.getTaxnumber(), client.getZipCode(), client.getId());
		return "redirect:/listClinet?clientUpdateSuccess";
	}
	
	@PostMapping("/deleteClient")
	public String deleteClient(@ModelAttribute("client") Client client) {
		Long id=client.getId();
	
		ticketService.deleteTicketByClient_ID(id);
	
		clientService.deleteById(client.getId());
		return "redirect:/listClient?clientDeleteSuccess";
	}


}
