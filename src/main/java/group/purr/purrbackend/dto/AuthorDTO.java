package group.purr.purrbackend.dto;

import lombok.Data;

@Data
public class AuthorDTO {

    public String username;

    public String password;

    public String email;

    public String description;

    @Override
    public String toString() {
        return "AuthorDTO{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
