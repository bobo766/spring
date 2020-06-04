package ru.korshun.eda.response.data;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SignInDataResponse {

    private int id;
    private String token;
    private String role;

    public SignInDataResponse(int id, String token, String role) {
        this.id = id;
        this.token = token;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public String getRole() {
        return role;
    }
}
