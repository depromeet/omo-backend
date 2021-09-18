package com.depromeet.omobackend.repository.user;

import com.depromeet.omobackend.domain.user.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
