package com.kite.scouter.global.listener;

import com.kite.scouter.domain.user.model.User;
import com.kite.scouter.domain.user.repository.UserRepository;
import com.kite.scouter.global.enums.LoginType;
import com.kite.scouter.global.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.Ordered;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.wildfly.common.annotation.NotNull;

@Component
@RequiredArgsConstructor
public class UserLoaderListener implements ApplicationListener<ApplicationStartedEvent>, Ordered {


  private final UserRepository userRepository;

  private final PasswordEncoder passwordEncoder;

  @Override
  public int getOrder() {
    return 0;
  }

  @Override
  public void onApplicationEvent(@NotNull final ApplicationStartedEvent event) {

    User user = User.builder()
      .email("test1@naver.com")
      .password(passwordEncoder.encode("1234"))
      .nickName("테스트1")
      .Type(LoginType.NAVER)
      .role(Role.USER)
      .build();

    createUserAssertNull(user);
  }

  private void createUserAssertNull(final User user) {
    if (userRepository.findByEmailAndNickName(user.getEmail(), user.getNickName()).isEmpty()) {
      userRepository.save(user);
    }
  }

}
