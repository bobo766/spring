package ru.korshun.eda.response.data;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDataResponse {

    private int id;
    private String token;
    private String role;
    private String name;

    public UserDataResponse(int id, String token, String role, String name) {
        this.id = id;
        this.token = token;
        this.role = role;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }
}
