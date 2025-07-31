package dev.salonce.atiperatask.Dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BranchDto {

    private String name;

    @JsonProperty("commit")
    private CommitDto commitDto;

    public BranchDto() {}

    public String getName() {
        return name;
    }

    public CommitDto getCommitDto(){
        return commitDto;
    }
}
