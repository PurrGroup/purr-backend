package group.purr.purrbackend.dto;

import lombok.Data;

@Data
public class AuthorDTO {

    public String username;

    public String email;

    public String description;

    @Override
    public String toString() {
        return "AuthorDTO{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
