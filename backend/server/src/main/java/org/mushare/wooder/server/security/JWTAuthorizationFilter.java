package org.mushare.wooder.server.security;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.io.IOException;
import java.security.Key;
import java.util.stream.Collectors;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.mushare.wooder.server.utils.Utils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Slf4j
public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

  public JWTAuthorizationFilter(
      AuthenticationManager authenticationManager) {
    super(authenticationManager);
  }

  @Override
  protected void doFilterInternal(HttpServletRequest req,
      HttpServletResponse res,
      FilterChain chain) throws IOException, ServletException {
    String header = req.getHeader(SecurityConstants.HEADER_STRING);
    if (header == null || !header.startsWith(SecurityConstants.TOKEN_PREFIX)) {
      chain.doFilter(req, res);
      return;
    }

    UsernamePasswordAuthenticationToken authentication = getAuthentication(req);
    SecurityContextHolder.getContext().setAuthentication(authentication);
    chain.doFilter(req, res);
  }

  private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
    String rawToken = request.getHeader(SecurityConstants.HEADER_STRING);
    if (rawToken != null) {
      try {
        Key key = Keys.hmacShaKeyFor(SecurityConstants.SECRET.getBytes());
        String sub = Jwts.parser().setSigningKey(key)
            .parseClaimsJws(rawToken.replace(SecurityConstants.TOKEN_PREFIX, "")).getBody()
            .getSubject();

        Token token = Utils.mapper.readValue(sub, Token.class);
        return new UsernamePasswordAuthenticationToken(token.getEmail(), null,
            token.getAuthorities().stream().map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList()));

      } catch (IOException exception) {
        log.error("convert json failed", exception);
      } catch (ExpiredJwtException ignore) {
      }

      return null;
    }
    return null;
  }

}
