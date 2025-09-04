package dev.salonce.githubrepositories.infrastructure.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CommitDto (@JsonProperty("sha") String sha) {}
