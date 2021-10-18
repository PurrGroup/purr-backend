package group.purr.purrbackend.service;

public interface TokenService {
    Boolean checkTokenAuthorizationHeader(String header);
}
