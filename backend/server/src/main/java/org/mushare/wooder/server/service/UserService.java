package org.mushare.wooder.server.service;

import com.google.common.base.Strings;
import java.util.Set;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mushare.wooder.server.repository.AuthorityDto;
import org.mushare.wooder.server.repository.AuthorityRepository;
import org.mushare.wooder.server.repository.UserDto;
import org.mushare.wooder.server.repository.UserRepository;
import org.mushare.wooder.server.security.SecurityConstants.Authority;
import org.mushare.wooder.spec.request.UserRequest;
import org.mushare.wooder.spec.response.GroupUserListResponse;
import org.mushare.wooder.spec.response.GroupUserListResponse.GroupUserItem;
import org.mushare.wooder.spec.response.OperationResponse;
import org.mushare.wooder.spec.response.UserResponse;
import org.mushare.wooder.spec.response.UserResponse.UserItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserService {

  private UserRepository userRepository;
  private AuthorityRepository authorityRepository;
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  public UserResponse getAllUsers() {
    return UserResponse
        .builder()
        .users(userRepository.findAll().stream().map(this::toUserItem).collect(Collectors.toList()))
        .build();
  }

  private UserItem toUserItem(UserDto userDto) {
    return UserItem
        .builder()
        .username(userDto.getUsername())
        .password(userDto.getPassword())
        .build();
  }

  public OperationResponse createUser(UserRequest userRequest) {
    if (!userRepository.existsByUsername(userRequest.getUsername())) {
      if (Strings.isNullOrEmpty(userRequest.getPassword()) || Strings
          .isNullOrEmpty(userRequest.getUsername())) {
        return OperationResponse.builder().succeed(false).message("username or password is null")
            .build();
      }
      Set<AuthorityDto> authorities = authorityRepository.findByAuthorityIn(
          userRequest.getAuthorities().stream().map(Authority::fromString)
              .collect(Collectors.toList()));
      try {
        userRepository.save(
            UserDto.builder().username(userRequest.getUsername())
                .password(bCryptPasswordEncoder.encode(userRequest.getPassword()))
                .authorities(authorities)
                .email(userRequest.getEmail())
                .createdAt(System.currentTimeMillis())
                .updatedAt(System.currentTimeMillis())
                .build());
        return OperationResponse.builder().succeed(true).build();
      } catch (Exception ex) {
        log.error("create user error", ex);
        return OperationResponse.builder().succeed(false).message("create user error").build();
      }
    } else {
      return OperationResponse.builder().succeed(false).message("username has been used").build();
    }
  }

  @Transactional
  public GroupUserListResponse getGroupUserList(long groupId, int pageNumber, int pageSize) {
    Page<UserDto> userDtoPage = userRepository
        .findByGroupDtoId(groupId, PageRequest.of(pageNumber, pageSize));
    return GroupUserListResponse.builder()
        .totalCount(userDtoPage.getTotalElements())
        .groupUserList(userDtoPage.get().map(userDto -> GroupUserItem
            .builder()
            .id(userDto.getId())
            .email(userDto.getEmail())
            .updateTime(userDto.getUpdatedAt())
            .createTime(userDto.getCreatedAt())
            .build())
            .collect(Collectors.toList()))
        .build();
  }
}
