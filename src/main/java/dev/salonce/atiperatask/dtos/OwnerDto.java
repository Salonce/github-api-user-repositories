package dev.salonce.atiperatask.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OwnerDto {
    private String login;

    public OwnerDto() {}

    public String getLogin() {
        return login;
    }
}
