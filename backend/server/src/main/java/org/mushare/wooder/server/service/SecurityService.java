package org.mushare.wooder.server.service;

import java.util.Arrays;
import javax.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mushare.wooder.server.repository.AuthorityDto;
import org.mushare.wooder.server.repository.AuthorityRepository;
import org.mushare.wooder.server.repository.UserDto;
import org.mushare.wooder.server.repository.UserRepository;
import org.mushare.wooder.server.security.SecurityConstants.Authority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Transactional
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class SecurityService implements UserDetailsService {

  private AuthorityRepository authorityRepository;
  private UserRepository userRepository;

  public void initializeAuthorities() {
    Arrays.stream(Authority.values()).forEach(authority -> {
      if (!authorityRepository.existsByAuthority(authority)) {
        authorityRepository.save(AuthorityDto.builder().authority(authority).build());
      }
    });
    log.info("Initialized authorities: {}", Arrays.toString(Authority.values()));
  }

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    UserDto userDto = userRepository.findByEmail(email);
    if (userDto == null) {
      throw new UsernameNotFoundException(email);
    }
    return new User(String.valueOf(userDto.getEmail()), userDto.getPassword(),
        userDto.getAuthorities());
  }

  public UserDto getCurrentUser() throws Exception {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (!(authentication instanceof AnonymousAuthenticationToken)) {
      UserDto userDto = userRepository.findByEmail(authentication.getName());
      if (userDto == null) {
        throw new UsernameNotFoundException("Cannot find user");
      }
      return userDto;
    } else {
      throw new Exception("Unauthenticated");
    }
  }

  public long getCurrentUserId() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (!(authentication instanceof AnonymousAuthenticationToken)) {
      UserDto userDto = userRepository.findByEmail(authentication.getName());
      if (userDto != null) {
        return userDto.getId();
      }
      log.error("User not exist");
      return -1;
    } else {
      log.error("Unauthenticated User");
      return -1;
    }
  }
}
