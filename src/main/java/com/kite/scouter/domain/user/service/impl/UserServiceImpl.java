package com.kite.scouter.domain.user.service.impl;

import com.kite.scouter.domain.user.model.User;
import com.kite.scouter.domain.user.repository.UserRepository;
import com.kite.scouter.domain.user.service.UserService;
import com.kite.scouter.global.enums.ResponseCode;
import com.kite.scouter.global.exception.BadRequestException;
import com.kite.scouter.global.utils.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepo;

  @Override
  public User getUserById(Long userId) {
    return userRepo.findById(userId)
        .orElseThrow(() -> BadRequestException.of(ResponseCode.UY0001, "user.id.not.find"));
  }

  @Override
  public User getUserBySelf() {
    return userRepo.findByEmail(SecurityUtil.getUserEmail())
        .orElseThrow(() -> BadRequestException.of(ResponseCode.UY0001, "user.email.not.find"));
  }
}
