package dev.salonce.githubrepositories.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record BranchDto (String name, @JsonProperty("commit") CommitDto commitDto) { }
