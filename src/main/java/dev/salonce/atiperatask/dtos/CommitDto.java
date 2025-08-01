package dev.salonce.atiperatask.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CommitDto {

    @JsonProperty("sha")
    private String sha;

    public String getSha() {
        return sha;
    }
}
