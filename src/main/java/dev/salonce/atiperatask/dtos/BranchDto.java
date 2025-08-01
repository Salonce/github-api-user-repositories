package dev.salonce.atiperatask.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BranchDto {

    public BranchDto() {}

    private String name;

    @JsonProperty("commit")
    private CommitDto commitDto;

    public String getName() {
        return name;
    }

    public CommitDto getCommitDto(){
        return commitDto;
    }
}
