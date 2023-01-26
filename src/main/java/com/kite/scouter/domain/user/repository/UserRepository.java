package com.kite.scouter.domain.user.repository;

import com.kite.scouter.domain.user.model.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {

  Optional<User> findByEmail(String email);

  Optional<User> findByNickName(String nickName);

  Optional<User> findByEmailAndNickName(String email, String nickName);

}
