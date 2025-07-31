package dev.salonce.atiperatask;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BranchDto {

    private String name;

    @JsonProperty("commit")
    private CommitDto commit;

    public BranchDto() {}

    public String getName() {
        return name;
    }

    public String getLastCommitSha() {
        return commit != null ? commit.getSha() : null;
    }
}
