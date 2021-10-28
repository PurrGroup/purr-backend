package group.purr.purrbackend.dto;

import lombok.Data;

@Data
public class AuthorDTO {

    public String username;

    public String email;

    public String description;

    public String avatar;

    public String qq;

    public String blogTitle;

    public String favicon;

    @Override
    public String toString() {
        return "AuthorDTO{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", description='" + description + '\'' +
                ", blogTitle='" + blogTitle + '\'' +
                ", favicon='" + favicon + '\'' +
                '}';
    }
}
