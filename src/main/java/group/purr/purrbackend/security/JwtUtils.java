package group.purr.purrbackend.security;

import group.purr.purrbackend.constant.TokenConstants;
import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.DefaultJws;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class JwtUtils {

    public static Jws<Claims> parserToken(String token, String secretKey) {
        try {
            final Jws<Claims> jws = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return jws;
        } catch (ExpiredJwtException e) {
            return new DefaultJws<>(null, e.getClaims(), "");
        } catch (UnsupportedJwtException | SignatureException | IllegalArgumentException | IncorrectClaimException e) {
            // TODO: throw tokenParseException
            return null;
        }
    }

    public static Boolean checkIsExpired(Jws<Claims> jws) {
        return jws.getBody().getExpiration().before(new Date());
    }

    public static String tokenGeneration(Object userID, String userIDFieldName, Long expiredSecond, String secretKey) {
        Date expiredTime = Date.from(LocalDateTime.now().plusSeconds(expiredSecond).atZone(ZoneId.systemDefault()).toInstant());
        return Jwts.builder().setHeaderParam("typ", "JWT").
                setIssuedAt(new Date()).setExpiration(expiredTime).
                claim(userIDFieldName, userID).
                signWith(SignatureAlgorithm.HS256, secretKey).compact();
    }

//    public static Authentication buildAuthentication(Jws<Claims> jws, String userFiledName){
//        Object userId = jws.getBody().get(userFiledName);
//        TestingAuthenticationToken testingAuthenticationToken = new TestingAuthenticationToken(userId, null, new ArrayList<>(0));
//        SecurityContextHolder.getContext().setAuthentication(testingAuthenticationToken);
//        return SecurityContextHolder.getContext().getAuthentication();
//    }

    public static Date getExpiredTimeFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(TokenConstants.secretKey).parseClaimsJws(token).getBody();
        return claims.getExpiration();
    }
}
