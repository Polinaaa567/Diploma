package local.arch.infrastructure.token;

import java.util.Date;
import java.util.logging.Logger;

import javax.crypto.SecretKey;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class TokenKey implements ITokenKey {

    private static final SecretKey SECRET_KEY;
    private static final long EXPIRATION_TIME = 259200000;

    static {
        String secretString = "SVDSUHUGVGMKXDMKSACDVEURRHDSVCHJSBKSDBNCJKVHDSHJSDVNDJSVNDSJKVLSVDCNJAKCMALCZCDSMKSDLDKJHCNSKAJNAVHCDGUIEEHG";
        SECRET_KEY = Keys.hmacShaKeyFor(secretString.getBytes());
    }

    @Override
    public String generateToken(String login, String role) {
        
            Logger.getLogger("generate role: " + role)
                        .info("generate role " + role);
                        
        return Jwts.builder()
                .subject(login)
                .claim("role", role)
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SECRET_KEY)
                .compact();
    }

    @Override
    public Claims validateToken(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(SECRET_KEY)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (Exception e) {
            throw new SecurityException("Invalid token", e);
        }
    }

    @Override
    public boolean isTokenValid(String token) {
        try {
            Claims claims = validateToken(token);
            return !claims.getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public String getLoginFromToken(String token) {
        return validateToken(token).getSubject();
    }

    @Override
    public String getRoleFromToken(String token) {
        Claims claims = validateToken(token);
        return claims.get("role", String.class);
    }

    @Override
    public boolean isAdmin(String token) {
        try {
            String role = getRoleFromToken(token);
            Logger.getLogger("check role: " + role)
                        .info("check role " + role);
            return "Администратор".contains(role);
        } catch (Exception e) {
            return false;
        }
    }
}
