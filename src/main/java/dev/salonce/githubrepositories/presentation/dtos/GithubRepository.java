package dev.salonce.githubrepositories.presentation.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record GithubRepository(String name, @JsonProperty("owner_login") String ownerLogin, List<Branch> branches) {}
