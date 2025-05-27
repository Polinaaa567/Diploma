package local.arch.infrastructure.token;

import io.jsonwebtoken.Claims;


public interface ITokenKey {

    public String generateToken(String login, String role);

    public Claims validateToken(String token);

    public String getLoginFromToken(String token);

    public boolean isTokenValid(String token);

    public String getRoleFromToken(String token);

    public boolean isAdmin(String token);
} 
