package com.kite.scouter.domain.user.service;

import com.kite.scouter.domain.user.model.User;

public interface UserService {

  User getUserById(Long userId);
  User getUserBySelf();

}
