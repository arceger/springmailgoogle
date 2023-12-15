package com.javaweb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository repo;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

//		User user = repo.findByEmail(email);
//		if (user==null) {
//			throw new UsernameNotFoundException("usuario nao encontrado");
//		}
//
//		return new CustomUserDetails(user);
//
//		User tecnico = repo.findByRole(tecnico);
//		if (tecnico==null) {
//			throw new UsernameNotFoundException("tecnico nao encontrado");
//		}
//		{
//			return new CustomUserDetails(tecnico);
//
//		}

		User user = repo.findByEmail(email);
		if (user != null) {
			return new CustomUserDetails(user);
		}

		User tecnico = (User) repo.findByRole("tecnico");
		if (tecnico == null) {
			throw new UsernameNotFoundException("tecnico nao encontrado");
		}

		return new CustomUserDetails(tecnico);


	}

}

