package com.javaweb;


import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;


public class LoginMesage {

    @Setter
    @Getter
    private String message;

    @Getter
    @Setter
    private Boolean status;
    private String username;

    public LoginMesage(String message, Boolean status, String username) {
        this.message = message;
        this.status = status;
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}