package com.javaweb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class EmailControl {
//	   @Autowired private JavaMailSender mailSender;
//
//	    public String sendMail(String toEmail,
//                String body,
//                String subject) {
//
//	        SimpleMailMessage message = new SimpleMailMessage();
//
//	        message.setText(body);
//	        message.setTo(toEmail);
//	        message.setFrom("jesse.band@gmail.com");
//	        message.setSubject(subject);
//
//
//	        try {
//	            mailSender.send(message);
//	            return "Email enviado com sucesso!";
//
//
//	        } catch (Exception e) {
//	            e.printStackTrace();
//	            return "Erro ao enviar email.";
//	        }
//	    }

}
