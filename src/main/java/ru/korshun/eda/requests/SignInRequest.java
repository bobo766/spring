package ru.korshun.eda.requests;

import javax.validation.constraints.NotBlank;

public class SignInRequest {

    @NotBlank
    private String phone;

    @NotBlank
    private String password;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String login) {
        this.phone = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
