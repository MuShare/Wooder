package org.mushare.wooder.server.security;

public class SecurityConstants {

  public static final String SECRET = "39fXSY6CTSx6a7uzCbzHvdkM7RPQ8mbd";
  public static final long EXPIRATION_TIME = 60 * 60 * 24 * 1000; // 1 days
  public static final String TOKEN_PREFIX = "Bearer ";
  public static final String HEADER_STRING = "Authorization";

  public enum Authority {
    ADMIN("ADMIN"),
    USER("USER");

    private String authority;

    Authority(String authority) {
      this.authority = authority;
    }

    public static Authority fromString(String authorityString) {
      for (Authority authority : Authority.values()) {
        if (authority.getAuthority().equals(authorityString)) {
          return authority;
        }
      }
      throw new IllegalArgumentException("no such authority");
    }

    public String getAuthority() {
      return this.authority;
    }
  }
}
