package tests;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class User {

    public User(String name, String job) {
        this.name = name;
        this.job = job;
    }

    @JsonProperty("name")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("job")
    private String job;

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }


    public String createUserRequest() {
       return String.format( "{\"name\": \"%s\",\n\"job\": \"%s\"\n}", getName(), getJob());
    }
}
