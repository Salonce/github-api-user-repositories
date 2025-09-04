package dev.salonce.githubrepositories.infrastructure.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record OwnerDto(String login) {}
