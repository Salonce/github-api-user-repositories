package dev.salonce.atiperatask.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record GithubRepositoryDto
        (String name,
         @JsonProperty("owner") OwnerDto ownerDto,
         @JsonProperty("branches_url") String branchesUrl,
         @JsonProperty("fork") Boolean fork) {}