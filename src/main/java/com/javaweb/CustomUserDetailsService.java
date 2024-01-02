package com.javaweb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserRepository repo;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		User user = repo.findByEmail(email);
		if (user == null) {
			throw new UsernameNotFoundException("usuario nao encontrado");
		}

		return new CustomUserDetails(user);

	}
	public LoginMesage loginEmployee(@RequestBody User user) {
		User usuario = repo.findByEmail(user.getEmail());
		if (usuario != null) {
			String encodedPassword = usuario.getPassword();
			String username = usuario.getName();
			boolean isPwdRight = passwordEncoder.matches(user.getPassword(), encodedPassword);
			if (isPwdRight) {
				return new LoginMesage("Login Success", true, username);
			} else {
				return new LoginMesage("Login Failed", false, null);
			}
		}
		else {
			return new LoginMesage("Login Failed", false, null);
		}
	}

}

