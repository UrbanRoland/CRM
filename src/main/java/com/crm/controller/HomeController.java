package com.crm.controller;

import java.math.BigInteger;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.crm.model.Client;
import com.crm.model.Ticket;
import com.crm.model.User;
import com.crm.repository.RoleRepository;
import com.crm.service.ClientServiceImpl;
import com.crm.service.TicketServiceImpl;
import com.crm.service.UserDetailsImpl;
import com.crm.service.UserServiceImpl;
import com.crm.validators.AddTicketValidator;
import com.crm.validators.ClientValidator;
import com.crm.validators.PasswordVaidator;
import com.crm.validators.UpdateTicketValidator;
import com.crm.validators.UserDataValidator;
import com.crm.validators.UserRegistrationValidator;

@Controller
public class HomeController {
	
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	//services
	private ClientServiceImpl clientService;
	private TicketServiceImpl ticketService;
	private UserServiceImpl userService;
	private RoleRepository roleRepository;
	
	@Autowired
	public void setClientService(ClientServiceImpl clientService) {
		this.clientService = clientService;
	}
	
	@Autowired
	public void setTicketService(TicketServiceImpl ticketService) {
		this.ticketService = ticketService;
	}
	
	
	@Autowired
	public void setUserService(UserServiceImpl userService) {
		this.userService = userService;
	}

	@Autowired
	public void setRoleRepository(RoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}


	//validators
	private UserDataValidator userDataValidator;
	private PasswordVaidator passwordVaidator;
	private UserRegistrationValidator userRegistrationValidator;
	private UpdateTicketValidator updateTicketValidator;
	private ClientValidator clientValidator;
	private AddTicketValidator addTicketValidator;
	
	@Autowired
	public void setUserDataValidator(UserDataValidator userDataValidator) {
		this.userDataValidator = userDataValidator;
	}
	
	@Autowired
	public void setPasswordVaidator(PasswordVaidator passwordVaidator) {
		this.passwordVaidator = passwordVaidator;
	}	
	
	@Autowired
	public void setUserRegistrationValidator(UserRegistrationValidator userRegistrationValidator) {
		this.userRegistrationValidator = userRegistrationValidator;
	}
	
	@Autowired
	public void setUpdateTicketValidator(UpdateTicketValidator updateTicketValidator) {
		this.updateTicketValidator = updateTicketValidator;
	}

	@Autowired
	public void setClientValidator(ClientValidator clientValidator) {
		this.clientValidator = clientValidator;
	}
	
	@Autowired
	public void setAddTicketValidator(AddTicketValidator addTicketValidator) {
		this.addTicketValidator = addTicketValidator;
	}

	@RequestMapping(path = "/addTicket")
	public String addTicket(Model model) {
		
		model.addAttribute("ticket", new Ticket());
		model.addAttribute("client", clientService.findAll());
		
		return "addTicket";
	}

	@RequestMapping(path = "/addTicketToDatabase", method = RequestMethod.POST)
	public String addTicketToDatabase(@ModelAttribute("ticket") @Validated Ticket ticket, BindingResult bindingResult,
			Client client,Model model) {
		
		model.addAttribute("client", clientService.findAll());

		//a megadott adatok validalasa
		addTicketValidator.validate(ticket, bindingResult);
		if (bindingResult.hasErrors()) {return "addTicket";}

		// megkeresni a bejelentkezett felhasznalo azonositojat
		User user = userService.findById(authenticatedUserId());

		ticketService.saveTicket(ticket,user,client);

		return "redirect:/addTicket?ticketAddSuccess";
	}

	@RequestMapping(path = { "/listTicket", "/" })
	public String listTicket(Model model) {
		
	
		
		model.addAttribute("tickets", ticketService.findAll());
		model.addAttribute("ticket", new Ticket());
		model.addAttribute("roles", roleRepository.findAll());
		return "listTicket";
	}
	
	
	@RequestMapping(path = "/forwardTicket",method = RequestMethod.POST)
	public String forwardTicket(@ModelAttribute("ticket") Ticket ticket) {

		if(ticket.getUserGroup()==null) {return "redirect:/listTicket?ticketForwardedError";}
		
		ticketService.updateTicketGroup(ticket.getId(), ticket.getUserGroup());
		return "redirect:/listTicket?ticketForwardedSuccess";
	}
	
	@RequestMapping(path = "/forwardTicketDev",method = RequestMethod.POST)
	public String forwardTicketDev(@ModelAttribute("ticket") Ticket ticket) {	
		
		if(ticket.getUserGroup()==null) {return "redirect:/listTicketDev?ticketForwardedError";}
		
		ticketService.updateTicketGroup(ticket.getId(), ticket.getUserGroup());
		
		return "redirect:/listTicketDev?ticketForwardedSuccess";
	}
	
	@RequestMapping(path = "/forwardTicketMech",method = RequestMethod.POST)
	public String forwardTicketMech(@ModelAttribute("ticket") Ticket ticket) {
		
		if(ticket.getUserGroup()==null) {return "redirect:/listTicketMech?ticketForwardedError";}
		
		ticketService.updateTicketGroup(ticket.getId(), ticket.getUserGroup());
		return "redirect:/listTicketMech?ticketForwardedSuccess";
	}
	
	@RequestMapping(path = "/forwardTicketTest",method = RequestMethod.POST)
	public String forwardTicketTest(@ModelAttribute("ticket") Ticket ticket) {
				
		if(ticket.getUserGroup()==null) {return "redirect:/listTicketTest?ticketForwardedError";}
		
		ticketService.updateTicketGroup(ticket.getId(), ticket.getUserGroup());
		return "redirect:/listTicketTest?ticketForwardedSuccess";
	}	
	
	@RequestMapping(path = "/deleteTicket",method = RequestMethod.POST)
	public String deleteTicket(@ModelAttribute("ticket") Ticket ticket) {
		
		ticketService.deleteById(ticket.getId());
		return "redirect:/listTicket?ticketDeleteSuccess";
	}
	  @RequestMapping(path = "/userData/{id}",method = RequestMethod.GET)
	   public String userData(@PathVariable Long id, Model model){
		  
		  model.addAttribute("userData",userService.findById(id) );
		  return "userData";
	  }
	   
	  @RequestMapping(path = "/editClient/{id}",method = RequestMethod.GET)
	    public String editClient(@PathVariable Long id, Model model){
		  
		  model.addAttribute("editClient",clientService.findById(id) );
		  return "editClient";
	  }
	  
	  @RequestMapping(path = "/editClient/{id}",method = RequestMethod.POST)
	    public String updateClient(@ModelAttribute("editClient") @Validated Client editClient,BindingResult bindingResult){
		  
		  	Client editedClient = clientService.findById(editClient.getId());
			
		  	clientValidator.validate(editClient, bindingResult);
			if (bindingResult.hasErrors()) {
				return "editClient";
			}
			
			clientService.updateClient(editedClient, editClient);
		  return "redirect:/listClient?clientUpdateSuccess";
	  }
	  
	  @RequestMapping(path = "/editTicket/{id}",method = RequestMethod.GET)
	    public String editTicket(@PathVariable Long id, Model model){
		  
		  User authenticatedUser=userService.findById(authenticatedUserId());
		  
	      Ticket ticket = ticketService.findById(id).get();
	     	      
	   //csak az elerheto hibajegyeket tudjak modositani
	       if((( (ticket.getUserGroup()==null && (authenticatedUser.getRoles().toString().equals("[Fejlesztő]"))) || (authenticatedUser.getRoles().toString().equals("[Fejlesztő]") && !ticket.getUserGroup().equals("Fejlesztő")))
	    		|| (ticket.getUserGroup()==null && (authenticatedUser.getRoles().toString().equals("[Szerelő]"))) || (authenticatedUser.getRoles().toString().equals("[Szerelő]") && !ticket.getUserGroup().equals("Szerelő"))) ||
	    		((ticket.getUserGroup()==null && (authenticatedUser.getRoles().toString().equals("[Tesztelő]"))) || (authenticatedUser.getRoles().toString().equals("[Tesztelő]") && !ticket.getUserGroup().equals("Tesztelő")))) {	    	   
	    	   	return "redirect:/listTicket";   
	       }
	        
	        User user= userService.findById(ticket.getUser().getId());
	    	model.addAttribute("editTicket",ticket );
	    	model.addAttribute("editedTicket", new Ticket());
	    	model.addAttribute("user",user );
	
	        return "editTicket";
	    }
	  
	@RequestMapping(path = "/editTicket/{id}",method = RequestMethod.POST)
	public String updateTicket(@ModelAttribute("editTicket") @Validated Ticket editTicket,BindingResult bindingResult,
			Model model) {
	
		Ticket editedTicket = ticketService.findById(editTicket.getId()).get();
	
		model.addAttribute("user",userService.findById(editedTicket.getUser().getId()));
		
		updateTicketValidator.validate(editTicket, bindingResult);
		if (bindingResult.hasErrors()) {return "editTicket";}
	
		ticketService.updateTicket(editedTicket, editTicket);
		   
		//megkeresi a bejelentkezett felhasznalot
		User authenticatedUser=userService.findById(authenticatedUserId());
		
		if (authenticatedUser.getRoles().toString().equals("[Fejlesztő]")) {
			
			return "redirect:/listTicketDev?ticketUpdateSuccess";
		}else if(authenticatedUser.getRoles().toString().equals("[Tesztelő]")){
			return "redirect:/listTicketTest?ticketUpdateSuccess";
		}else if(authenticatedUser.getRoles().toString().equals("[Szerelő]")){
			return "redirect:/listTicketMech?ticketUpdateSuccess";
		}else {
			return "redirect:/listTicket?ticketUpdateSuccess";
		}
		
	}

	
	
	@RequestMapping(path = "/login")
	public String login() {return "login";}

	@RequestMapping(path = "/registration")
	public String registration(Model model) {
		model.addAttribute("user", new User());
		return "registration";
	}

	@RequestMapping(path = "/reg",method = RequestMethod.POST)
	public String reg(@ModelAttribute @Validated User user, BindingResult bindingResult) {

		userRegistrationValidator.validate(user, bindingResult);
		if (bindingResult.hasErrors()) {return "registration";}
		
		//kodoljuk a jelszavat, hogy ne egyszeru szovegkent keruljon az adatbazisba
		String encodedPassword = passwordEncoder.encode(user.getPassword());

		user.setPassword(encodedPassword);
		user.setPasswordConf(encodedPassword);

		userService.registerUser(user);

		return "redirect:/login?needActivation";
	}

	@RequestMapping(path = "/forgetPassword")
	public String forgetPassword(Model model) {
		model.addAttribute("user", new User());
		return "forgetPassword";
	}

	@RequestMapping(path = "/forgetPasswordAction",method = RequestMethod.POST)
	public String forgetPasswordAction(@ModelAttribute @Validated User user, BindingResult bindingResult,
			@RequestParam String email) {

		String response = userService.forgotPassword(email);

		if (!response.startsWith("Invalid")) {
			return "redirect:/login?emailSend";

		} else {return "redirect:/forgetPassword?emailNotFound";}

	}

	@RequestMapping(path  = "/confirm-reset")
	public String resetPassword(Model model, @RequestParam String token, @ModelAttribute User user,
			BindingResult bindingResult, HttpSession session) {

		session.setAttribute("token", user.getToken());

		model.addAttribute("user", user);
		return "resetPassword";
	}

	@RequestMapping(path = "/reset-password",method = RequestMethod.POST)
	public String resetPassword(@ModelAttribute @Validated User user, HttpSession session,
								BindingResult bindingResult) {
		String token = null;

		if (session.getAttribute("token") != null) {token = (String) session.getAttribute("token");}
		
		passwordVaidator.validate(user, bindingResult);
		if (bindingResult.hasErrors()) {return "resetPassword";}

		String encodedPassword = passwordEncoder.encode(user.getPassword());

		if (userService.resetPassword(token, encodedPassword) == "Invalid token.") {
			return "redirect:/login?invalidToken";
		} else if (userService.resetPassword(token, encodedPassword) == "Token expired.") {
			return "redirect:/login?tokenExpired";
		} else {return "redirect:/login?passwordChanged";}

	}

	@RequestMapping(path = "/activation/{code}", method = RequestMethod.GET)
	public String activation(@PathVariable("code") String code, HttpServletResponse response) {
		userService.userActivation(code);
		return "redirect:/login?activationSuccess";
	}

	@RequestMapping(path = "/settings")
	public String settings(Model model) {
		
		model.addAttribute("user", new User());
		return "settings";
	}

	@RequestMapping(path = "/updateProfile",method = RequestMethod.POST)
	public String updateUserName(@ModelAttribute @Validated User user, BindingResult bindingResult) {

		userDataValidator.validate(user, bindingResult);
		if (bindingResult.hasErrors()) {return "settings";}

		userService.updateUserNameAndEmail(userService.findById(authenticatedUserId()), user);
		return "redirect:/settings?updateProfileSuccess";
	}
	
	@RequestMapping(path = "/updateUserPicture",method = RequestMethod.POST)
	public String updateProfilePicture(@RequestParam("file") MultipartFile file) {

		if(file.isEmpty()) {return "redirect:/settings?updateError";}

		User modifiledUser=userService.findById(authenticatedUserId());	
		
		try {userService.addPhoto(file, modifiledUser);} catch (Exception e) {e.printStackTrace();}
		
		// auditalas kijatszasa
		modifiledUser.setImage(false);
		userService.save(modifiledUser);
		modifiledUser.setImage(true);
		userService.save(modifiledUser);		
		
		return "redirect:/settings?profilePictureUploadSuccess";
}
	

	@RequestMapping(path = "/updateUserPassword",method = RequestMethod.POST)
	public String updateUserPassword(@ModelAttribute @Validated User user, BindingResult bindingResult) {

		passwordVaidator.validate(user, bindingResult);
		if (bindingResult.hasErrors()) {return "settings";}

		user.setPassword( passwordEncoder.encode(user.getPassword()));
		user.setPasswordConf(passwordEncoder.encode(user.getPasswordConf()));
		
		userService.updateUserPassword(userService.findById(authenticatedUserId()),user);

		return "redirect:/settings?updateUserPasswordSuccess";
	}

	@RequestMapping(path = "addClient")
	public String addClient(Model model) {
		model.addAttribute("client", new Client());
		return "addClient";
	}

	@RequestMapping(path = "/addToClient",method = RequestMethod.POST)
	public String addToClient(@ModelAttribute @Validated Client client, BindingResult bindingResult) {
		
		clientValidator.validate(client, bindingResult);
		if (bindingResult.hasErrors()) {return "addClient";}

		clientService.saveClient(client);
		return "redirect:/addClient?clientAddSuccess";
	}


	@RequestMapping(path = "/listUsers")
	public String listUsers(Model model) {
		model.addAttribute("users", userService.findAll());
		model.addAttribute("user", new User());
		return "listUsers";
	}
	
	
	@RequestMapping(path = "/updateUserRole",method = RequestMethod.POST)
	public String updateUserRole(@ModelAttribute("user") @Validated User user) {

		User modifiledUser=userService.findById(user.getId());

		// a username valtozoba beletoltom a role-t
		Long role_id=userService.findByRoleName(user.getUsername());
	
		userService.updateUserRole(user.getId(), role_id);
		
		modifiledUser.setImage(false);
		userService.save(modifiledUser);
		modifiledUser.setImage(true);
		userService.save(modifiledUser);
		return "redirect:/listUsers?updateUserRoleSuccess";
	}
	
	@RequestMapping(path = "/deleteUser",method = RequestMethod.POST)
	public String deleteUser(@ModelAttribute("user") User user) {
		
		userService.deleteUsers_Roles(user.getId());
		userService.deleteById(user.getId());
		return "redirect:/listUsers?userDeleteSuccess";
	}
	

	@RequestMapping(path = "/displayPieCharts",method = RequestMethod.GET)
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
	
	@RequestMapping(path = "/displayLineCharts",method = RequestMethod.GET)
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
	@RequestMapping(path = "/listClient")
	public String listClien(Model model) {
		model.addAttribute("clients", clientService.findAll());
		return "listClient";
	}

	
	@RequestMapping(path = "/deleteClient",method = RequestMethod.POST)
	public String deleteClient(@ModelAttribute("client") Client client) {
	
		ticketService.deleteTicketByClient_ID(client.getId());
	
		clientService.deleteById(client.getId());
		return "redirect:/listClient?clientDeleteSuccess";
	}

	@RequestMapping(path = { "/listTicketDev"})
	public String listTicketDev(Model model) {
		
		List<Ticket> tickets = new ArrayList<>();

		ticketService.findAll().forEach(t -> {
			if(t.getUserGroup().equals("Fejlesztő")) {tickets.add(t);}});
		
		model.addAttribute("tickets", tickets);
		model.addAttribute("ticket", new Ticket());
		model.addAttribute("roles", roleRepository.findAll());
		return "listTicketDev";
	}

	@RequestMapping(path = { "/listTicketMech"})
	public String listTicketMech(Model model) {
		
		List<Ticket> tickets=new ArrayList<>();

		ticketService.findAll().forEach(t -> {
			if(t.getUserGroup().equals("Szerelő")) {tickets.add(t);}});
	
		model.addAttribute("tickets", tickets);
		model.addAttribute("ticket", new Ticket());
		model.addAttribute("roles", roleRepository.findAll());
		return "listTicketMech";
	}
	
	@RequestMapping(path = { "listTicketTest"})
	public String listTicketTest(Model model) {
		
		List<Ticket> tickets = new ArrayList<>();

		ticketService.findAll().forEach(t -> {
			if(t.getUserGroup().equals("Tesztelő")) {tickets.add(t);}});
		
	
		model.addAttribute("tickets", tickets);
		model.addAttribute("ticket", new Ticket());
		model.addAttribute("roles", roleRepository.findAll());
		return "listTicketTest";
	}
	
	 @RequestMapping(path = "/default")
	    public String defaultAfterLogin() {
		
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			String authenticatedUserRole = null;
			if (principal instanceof UserDetails) {
				authenticatedUserRole = ((UserDetailsImpl) principal).getRole();
			} else {
				authenticatedUserRole = principal.toString();
			}
			
			
			if (authenticatedUserRole.equals("[Vezető]")) {
				return "redirect:/listTicket";
			} else if (authenticatedUserRole.equals("[Ügyintéző]")) {
				return "redirect:/listTicket";
			} else if (authenticatedUserRole.equals("[Fejlesztő]")) {
				return "redirect:/listTicketDev";
			} else if (authenticatedUserRole.equals("[Szerelő]")) {
				return "redirect:/listTicketMech";
			} else if (authenticatedUserRole.equals("[Tesztelő]")) {
				return "redirect:/listTicketTest";
			} else {
				return "Hiba";
			}
	 }
	 
	 
	
	 
	 //seged fuggvenyek
	  
		@InitBinder
		public void initBinder(WebDataBinder binder) {
			binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
		}
	  
		private Long authenticatedUserId() {
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Long authenticatedUserId = null;
			if (principal instanceof UserDetails) {
				authenticatedUserId = ((UserDetailsImpl) principal).getId();
			} else {
				authenticatedUserId = (Long) principal;
			}
			return authenticatedUserId;

		}
		
		//lecsereli az ekezetes karakterekete
		public String stripAccents(String s){
		    s = Normalizer.normalize(s, Normalizer.Form.NFD);
		    s = s.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
		    return s;
		}
		
	

}
