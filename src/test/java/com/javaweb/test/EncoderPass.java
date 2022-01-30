package com.javaweb.test;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class EncoderPass {

	public static void main(String[] args) {
		BCryptPasswordEncoder code = new BCryptPasswordEncoder();
		
		String passFlat = "123@Mudar";
		String encoPass = code.encode(passFlat);
		
		System.out.println(encoPass);
		

	}

}
