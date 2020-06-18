package ru.korshun.eda.requests;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class SignUpRequest {

    @NotBlank
    @Size(min = 4, max = 40)
    private String name;

//    @NotBlank
//    @Size(min = 6, max = 20)
//    private String password;

    @NotBlank
    @Size(min = 10, max = 10)
    private String phone;

    @NotBlank
    private String role;


    public String getName() {
        return name;
    }

//    public String getPassword() {
//        return password;
//    }

    public String getPhone() {
        return phone;
    }

    public String getRole() {
        return role;
    }
}