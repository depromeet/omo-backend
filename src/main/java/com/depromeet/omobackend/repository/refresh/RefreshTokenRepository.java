package com.depromeet.omobackend.repository.refresh;

import com.depromeet.omobackend.domain.refreshtoken.RefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {
    void deleteAllByEmail(String email);
}
