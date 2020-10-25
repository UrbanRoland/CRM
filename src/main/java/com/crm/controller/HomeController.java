package com.crm.controller;

import java.math.BigInteger;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
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
import com.crm.service.UserServiceImpl;
import com.crm.validators.AddTicketValidator;
import com.crm.validators.ClientValidator;
import com.crm.validators.PasswordVaidator;
import com.crm.validators.UpdateTicketValidator;
import com.crm.validators.UserDataValidator;
import com.crm.validators.UserRegistrationValidator;

@Controller
public class HomeController {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	//services
	private ClientServiceImpl clientService;
	private TicketServiceImpl ticketService;
	private UserServiceImpl userService;
	
	
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



	//validators
	private UserDataValidator userDataValidator;
	private PasswordVaidator passwordVaidator;
	private UserRegistrationValidator userRegistrationValidator;
	private UpdateTicketValidator updateTicketValidator;
	private ClientValidator clientValidator;
	
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

		//ticket.setCreationDate(LocalDateTime.now());
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

		User user = userService.findById(authenticatedUserId);
		ticket.setUser(user);

		ticketService.saveTicket(ticket);

		return "redirect:/addTicket?ticketAddSuccess";
	}

	@RequestMapping(value = { "listTicket", "/" })
	public String listTicket(Model model) {
		model.addAttribute("tickets", ticketService.findAll());
		model.addAttribute("ticket", new Ticket());
		model.addAttribute("roles", userService.findRolesWithoutVezetoandUgyintezo());
		return "listTicket";
	}
	
	
	@PostMapping("/forwardTicket")
	public String forwardTicket(@ModelAttribute("ticket") Ticket ticket) {
		//System.out.println("Azonosító: "+ticket.getId());
		//System.out.println("Csoport: "+ticket.getTitle());
		String group=ticket.getTitle();
		if(group==null) {
			return "redirect:/listTicket?ticketForwardedError";
		}
		
		ticketService.updateTicketGroup(group,true ,ticket.getId());
		return "redirect:/listTicket?ticketForwardedSuccess";
	}
	
	@PostMapping("/forwardTicketDev")
	public String forwardTicketDev(@ModelAttribute("ticket") Ticket ticket) {
		
		String group=ticket.getTitle();
		if(group==null) {return "redirect:/listTicketDev?ticketForwardedError";}
		
		ticketService.updateTicketGroup(group,true ,ticket.getId());
		return "redirect:/listTicketDev?ticketForwardedSuccess";
	}
	@PostMapping("/forwardTicketMech")
	public String forwardTicketMech(@ModelAttribute("ticket") Ticket ticket) {
		
		String group=ticket.getTitle();
		if(group==null) {return "redirect:/listTicketMech?ticketForwardedError";}
		
		ticketService.updateTicketGroup(group,true ,ticket.getId());
		return "redirect:/listTicketMech?ticketForwardedSuccess";
	}
	@PostMapping("/forwardTicketTest")
	public String forwardTicketTest(@ModelAttribute("ticket") Ticket ticket) {
		
		String group=ticket.getTitle();
		if(group==null) {return "redirect:/listTicketTest?ticketForwardedError";}
		
		ticketService.updateTicketGroup(group,true ,ticket.getId());
		return "redirect:/listTicketTest?ticketForwardedSuccess";
	}
	
	
	
	@PostMapping("/deleteTicket")
	public String deleteTicket(@ModelAttribute("ticket") Ticket ticket) {
		ticketService.deleteById(ticket.getId());
		return "redirect:/listTicket?ticketDeleteSuccess";
	}
	  @GetMapping("/userData/{id}")
	    public String userData(@PathVariable Long id, Model model){
		  User user=userService.findById(id);
		  model.addAttribute("userData",user );
		  return "userData";
	  }
	  
	  
	  @GetMapping("/editClient/{id}")
	    public String editClient(@PathVariable Long id, Model model){
		  
		  Client client = clientService.findById(id);
			model.addAttribute("editClient",client );
		  return "editClient";
	  }
	  
	  @PostMapping("/editClient/{id}")
	    public String updateClient(@ModelAttribute("editClient") @Validated Client editClient,BindingResult bindingResult){
		  
		  	Client editedClient = clientService.findById(editClient.getId());
			
		  	clientValidator.validate(editClient, bindingResult);
			if (bindingResult.hasErrors()) {
				return "editClient";
			}
			
			clientService.updateClient(editedClient, editClient);
			
		  return "redirect:/listClient?clientUpdateSuccess";
	  }
	  
	  @GetMapping("/editTicket/{id}")
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
	    	model.addAttribute("modifiedUserAndDate",ticketService.modifiedUserAndDate(id));
	    	
	    	
	    	modifiedUserAndDate(model,id);
	    	
	    	
	        return "editTicket";
	    }
	  
	@PostMapping("/editTicket/{id}")
	public String updateTicket(@ModelAttribute("editTicket") @Validated Ticket editTicket,BindingResult bindingResult,
			Model model) {
	
		Ticket editedTicket = ticketService.findById(editTicket.getId()).get();
		
		User user= userService.findById(editedTicket.getUser().getId());
	
		model.addAttribute("user",user );
		model.addAttribute("modifiedUserAndDate",ticketService.modifiedUserAndDate(editTicket.getId()));
		modifiedUserAndDate(model,editedTicket.getId());
		updateTicketValidator.validate(editTicket, bindingResult);
		if (bindingResult.hasErrors()) {
		
			return "editTicket";
		}
	
	 
		ticketService.updateTicket(editedTicket, editTicket);
		   

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

		userRegistrationValidator.validate(user, bindingResult);
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

		
		passwordVaidator.validate(user, bindingResult);
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

	@PostMapping("/updateProfile")
	public String updateUserName(@ModelAttribute @Validated User user, BindingResult bindingResult) {

		userDataValidator.validate(user, bindingResult);
		if (bindingResult.hasErrors()) {
			return "settings";
		}

		
		User modifiledUser=userService.findById(authenticatedUserId());
		userService.updateUserNameAndEmail(modifiledUser, user);
		
		return "redirect:/settings?updateProfileSuccess";
	}
	
	@PostMapping("/updateUserPicture")
	public String updateProfilePicture(@RequestParam("file") MultipartFile file) {

		if(file.isEmpty()) {
			return "redirect:/settings?updateError";
		}

		User modifiledUser=userService.findById(authenticatedUserId());	
		
		try {
			userService.addPhoto(file, modifiledUser);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// auditalas kijatszasa
		modifiledUser.setImage(false);
		userService.save(modifiledUser);
		modifiledUser.setImage(true);
		userService.save(modifiledUser);		
		
		return "redirect:/settings?profilePictureUploadSuccess";
}
	

	@PostMapping("/updateUserPassword")
	public String updateUserPassword(@ModelAttribute @Validated User user, BindingResult bindingResult) {

		passwordVaidator.validate(user, bindingResult);
		if (bindingResult.hasErrors()) {
			return "settings";
		}

		user.setPassword( passwordEncoder.encode(user.getPassword()));
		user.setPasswordConf(passwordEncoder.encode(user.getPasswordConf()));
		
		User modifiledUser=userService.findById(authenticatedUserId());
		
		userService.updateUserPassword(modifiledUser,user);

		return "redirect:/settings?updateUserPasswordSuccess";
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

		User user = userService.findById(authenticatedUserId);

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
	public String updateUseerRole(@ModelAttribute("user") @Validated User user) {

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
		
		
		return "listClient";
	}

	
	@PostMapping("/deleteClient")
	public String deleteClient(@ModelAttribute("client") Client client) {
		Long id=client.getId();
	
		ticketService.deleteTicketByClient_ID(id);
	
		clientService.deleteById(client.getId());
		return "redirect:/listClient?clientDeleteSuccess";
	}

	@RequestMapping(value = { "listTicketDev"})
	public String listTicketDev(Model model) {
		model.addAttribute("tickets", ticketService.findTicketsByDevGroup());
		model.addAttribute("ticket", new Ticket());
		model.addAttribute("roles", userService.findRolesWithoutVezetoandUgyintezo());
		return "listTicketDev";
	}
	@RequestMapping(value = { "listTicketMech"})
	public String listTicketMech(Model model) {
		model.addAttribute("tickets", ticketService.findTicketsByMechGroup());
		model.addAttribute("ticket", new Ticket());
		model.addAttribute("roles", userService.findRolesWithoutVezetoandUgyintezo());
		return "listTicketMech";
	}
	@RequestMapping(value = { "listTicketTest"})
	public String listTicketTest(Model model) {
		model.addAttribute("tickets", ticketService.findTicketsByTestGroup());
		model.addAttribute("ticket", new Ticket());
		model.addAttribute("roles", userService.findRolesWithoutVezetoandUgyintezo());
		return "listTicketTest";
	}
	
	 @RequestMapping("/default")
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
		
		private void modifiedUserAndDate(Model model,Long id) {
			  for (Object[] ob : ticketService.modifiedUserAndDate(id)){
    			  if((String)ob[0]==null) {
    		    	   model.addAttribute("modifiedName","Nem módosított");
    		       }else {
    		    		model.addAttribute("modifiedName",(String)ob[0]);
    		       }
    			  
    		        if((Date)ob[1]==null) {	    		        
    		        	model.addAttribute("modifiedDate","Nem módosított");
    		        }else {	    		        	
    		        	model.addAttribute("modifiedDate",(Date)ob[1]);
    		        }	    	
    		    }
		}
	

}
