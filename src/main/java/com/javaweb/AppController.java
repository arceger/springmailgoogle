package com.javaweb;

import java.util.List;

import com.javaweb.order.Orders;
import com.javaweb.order.OrderRepo;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AppController {

	private final CustomUserDetailsService customUserDetailsService;

	@Autowired
	private UserRepository repo;

	@Autowired
	private OrderRepo repoOrder;

	@Autowired
	private EmailControl emailSender;

    public AppController(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @EventListener(ApplicationReadyEvent.class)

	@GetMapping("")
	public String viewHomePage() {
		return "index";
	}


	@GetMapping("/register")

	public String showSigUpForm(Model model ) {

		model.addAttribute("user", new User());

		return "register";
	}
//
//	@PostMapping("/processa_registro")
//	public String processRegistration(User user) {
//
//		BCryptPasswordEncoder code = new BCryptPasswordEncoder();
//		String encoPass = code.encode(user.getPassword());
//		user.setPassword(encoPass);
//		repo.save(user);
//
//
////		String usrMail = user.getEmail();
////		String usrName = user.getName();
////		emailSender.sendMail(usrMail, "Sejas bem Vindo "+usrName, "Confirmaçao de Registro Spring Boot");
////
//		return "sucess";
//	}

	@PostMapping("/processa_registro")
	public String processRegistration(User user) {
		try {
			BCryptPasswordEncoder code = new BCryptPasswordEncoder();
			String encoPass = code.encode(user.getPassword());
			user.setPassword(encoPass);
			repo.save(user);
			return "sucess";
		} catch (DataIntegrityViolationException e) {
			if (e.getCause() instanceof ConstraintViolationException) {
				ConstraintViolationException cause = (ConstraintViolationException) e.getCause();
				String errorMessage = cause.getMessage();
				if (errorMessage.contains("duplicate key value violates unique constraint")) {
					System.out.println("E-mail duplicado: " + errorMessage);
					return "pgerror";
				} else {
					return "pgerror";
				}
			} else {
				return "pgerror";
			}
		}
	}






	@GetMapping("/userlist")  // listar todos os usuarios
	public String viewUsersList(Model model) {
		List<User> listUsers = repo.findAll();
		model.addAttribute("listUsers", listUsers);
		return "users";
	}

	@GetMapping("/teclist")  // listar todos os usuários
	public String viewTecList(Model model) {
		List<User> listUsers = repo.findByRole("tec");
		model.addAttribute("listUsers", listUsers);
		return "users";
	}


	@GetMapping("/incidentes") //registrar novo incidente
	public String showFormIncidentes(Model model) {
		model.addAttribute("orders", new Orders());
		List<User> listUsers = repo.findByRole("tec");
		model.addAttribute("listTec", listUsers);
		return "incidentes";
	}

	/*
	 model.addAttribute("orders", new Orders());
        List<User> tecnicos = customUserDetailsService.listarTecnicos();
        model.addAttribute("tecnicos", tecnicos);
        return "incidentes";
	 */


	@PostMapping("/processa_incidente")
	public String procesaIncidente(Orders orders) {

		repoOrder.save(orders);

		return "sucess";
	}

	 @RequestMapping("/login")
	  public String login() {
	    return "login";
	  }


	@GetMapping("/orderlist") // listar os incidentes
	public String viewOrderList(Model model) {
		List<Orders> listOrder = repoOrder.findAll();
		model.addAttribute("listOrder", listOrder);
		return "orderlist";
	}



}
