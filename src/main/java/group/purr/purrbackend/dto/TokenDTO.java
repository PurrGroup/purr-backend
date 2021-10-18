package group.purr.purrbackend.dto;

import lombok.Data;

import java.util.Date;

@Data
public class TokenDTO {
    private String accessToken;

    private String refreshToken;

    private String accessExpiredTime;
}
