package com.javaweb;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AppController {
	
	@Autowired
	private UserRepository repo;
	
	@Autowired
	private EmailControl emailSender;
	
	@EventListener(ApplicationReadyEvent.class)
	
	@GetMapping("")
	public String viewHomePage() {
		return "index";
	}
	
	
	  @GetMapping("/ordersrv")
	  public String viewUsersList(Model model) {
		  List<User> listUsers = repo.findAll();
		  model.addAttribute("listUsers", listUsers);
		  return "users";
		  }
	 
	
	@GetMapping("/register")
	
	public String showSigUpForm(Model model ) {
		
		model.addAttribute("user", new User());
		
		return "register";
	}
	
	@PostMapping("/processa_registro")
	public String processRegistration(User user) {
		
		BCryptPasswordEncoder code = new BCryptPasswordEncoder();
		String encoPass = code.encode(user.getPassword());
		user.setPassword(encoPass);
		repo.save(user);
		
		String usrMail = user.getEmail();
		String usrName = user.getName();
		emailSender.sendMail(usrMail, "Sejas bem Vindo "+usrName, "Confirmaçao de Registro Spring Boot");
		
		return "sucess";
		
		
		
		
	}
	
	 @RequestMapping("/login")
	  public String login() {
	    return "login";
	  }

}
