package com.javaweb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {


	
@Query("SELECT u FROM User u WHERE u.email = ?1")
User findByEmail(String email);
@Query("SELECT u FROM User u WHERE u.role = :role")
List<User> findByRole(@Param("role") String role);

    Optional<User> findOneByEmailAndPassword(String email, String password);


}
