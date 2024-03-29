package com.javaweb;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.javaweb.order.*;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;
@Controller
public class AppController {
	private final CustomUserDetailsService customUserDetailsService;
	@Autowired
	private UserRepository repo;
	@Autowired
	private OrderRepo repoOrder;
	@Autowired
	private CustomUserDetailsService userDetailsService;
	@Autowired
//	private EmailControl emailSender;
    public AppController(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }
    @EventListener(ApplicationReadyEvent.class)
	@GetMapping("")
	public String viewHomePage() {
		return "index";
	}
	@GetMapping("/welcome")
	public String viewPage() {
		return "welcome";
	}
	@GetMapping("/register") //registrar usuario
	public String showSigUpForm(Model model ) {
		model.addAttribute("user", new User());
		return "register";
	}
	@PostMapping("/processa_registro")// processar e validar usuario
	public String processRegistration(User user) {
		try {
			BCryptPasswordEncoder code = new BCryptPasswordEncoder();
			String encoPass = code.encode(user.getPassword());
			user.setPassword(encoPass);
			repo.save(user);
//			String usrMail = user.getEmail();
//			String usrName = user.getFirstName();
//			emailSender.sendMail(usrMail, "Sejas bem Vindo "+usrName, "Confirmaçao de Registro Spring Boot");
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

	@GetMapping("/teclist")  // listar todos os tecnicos
	public String viewTecList(Model model) {
		List<User> listUsers = repo.findByRole("tec");
		model.addAttribute("listUsers", listUsers);
		return "users";
	}

	@GetMapping("/orderteclist/{tecName}") // listar todos os chamados por tecnico
	public String viewTec(@PathVariable String tecName, Model model) {
		List<Orders> listOrder = repoOrder.findByTecnico(tecName);
		model.addAttribute("listOrder", listOrder);
		return "orderlist";
	}


	@GetMapping("/incidentes") //registrar novo incidente
	public String showFormIncidentes(Model model) {
		model.addAttribute("orders", new Orders());
		List<User> listUsers = repo.findByRole("tec");//listar tecnico para alocar no incidente
		model.addAttribute("listTec", listUsers);
		return "incidentes";
	}

			  @PostMapping("/listorder") // grava o update
			  public String editUser(@RequestBody @Valid AtualizaCall updateOrder, @PathVariable Long orderId, Model model) {

				  Orders order = repoOrder.findById(orderId);

				  if (order == null) {
					  return "pgerror"; // ou redirecione para uma página de erro, se desejar
				  }

				  if (updateOrder.getStatus() != null) {
					  order.setStatus(updateOrder.getStatus());
				  }

				  if (updateOrder.getDefeito() != null) {
					  order.setDefeito(updateOrder.getDefeito());
				  }

				  Orders updatedOrder = repoOrder.save(order);

				  // Adicione qualquer atributo ao model que você deseja usar na view de sucesso
				  model.addAttribute("mensagem", "Chamado atualizado com sucesso!");

				  // Retorne o nome da view de sucesso
				  return "sucess";
			  }


	@PostMapping("/processa_incidente")
	public String procesaIncidente(Orders orders) throws IOException {

		orders.setAbertura(LocalDateTime.now());
//		OrderPhoto orderPhoto = new OrderPhoto();
//		orderPhoto.setOrder(orders);
//		orderPhoto.setPhoto(photo.getBytes()); // Assuming photo is stored as byte array

		repoOrder.save(orders);

		return "sucess";
	}

	 @RequestMapping("/login")
	  public String login() {
	    return "login";
	  }


	@GetMapping("/orderlist") // listar todos os incidentes
	public String viewOrderList(Model model) {
		List<Orders> listOrder = repoOrder.findAll();
		model.addAttribute("listOrder", listOrder);
		return "orderlist";
	}


	@GetMapping("/listcall/{orderId}")  //listar detalhe do incidente por id
	public String viewOrderCall(@PathVariable Long orderId, Model model) {
		Orders order = repoOrder.findById(orderId);
		model.addAttribute("listCall", order);
		return "orderCall";//chama listorder
	}


	// **** api  Angular ////   ///////
	@RestController
	@RequestMapping("/api")
	@PreAuthorize("isAuthenticated()")
	public class UserController {
		@Autowired
		private CustomUserDetailsService userDetailsService;
		@PostMapping("/login")
		public ResponseEntity<?> loginEmployee(@RequestBody User user)
		{
			LoginMesage loginResponse = customUserDetailsService.loginEmployee(user);
			return ResponseEntity.ok(loginResponse);
		}

		@GetMapping("/userlist") //listar todos usuarios
		public List<User> viewUsersList() {
			List<User> listUsers = repo.findAll();
			return listUsers;
		}

		@GetMapping("/orderlist") // listar todos os incidentes
		public List<Orders> viewOrderList() {
			List<Orders> orderapi = repoOrder.findAll();
			return orderapi;
		}
		@GetMapping("/listcal/{orderId}") //listar apenas um incidente pelo id
		@ResponseBody
		public ResponseEntity<List<Orders>> viewOrderCall(@PathVariable long orderId) {
			Optional<Orders> orderOptional = Optional.ofNullable(repoOrder.findById(orderId));

			if (orderOptional.isPresent()) {
				Orders order = orderOptional.get();
				List<Orders> orderList = Collections.singletonList(order);
				return ResponseEntity.ok(orderList);
			} else {
				return ResponseEntity.notFound().build();
			}
		}

		@Autowired
		private OrderRepo repoOrder;

		@PutMapping("/updateOrder/{id}")
		public ResponseEntity<Orders> updateOrder(@PathVariable Long id, @RequestBody Orders updatedOrder) {
			Optional<Orders> existingOrderOptional = Optional.ofNullable(repoOrder.findById(id));

			if (existingOrderOptional.isPresent()) {
				Orders existingOrder = existingOrderOptional.get();

				// Atualiza apenas os campos desejados
				existingOrder.setStatus(updatedOrder.getStatus());
				existingOrder.setDefeito(updatedOrder.getDefeito());

				// Salva a ordem atualizada
				Orders savedOrder = repoOrder.save(existingOrder);
				return new ResponseEntity<>(savedOrder, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		}

	}

}