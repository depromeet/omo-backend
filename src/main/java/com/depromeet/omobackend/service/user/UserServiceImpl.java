package com.depromeet.omobackend.service.user;

import com.depromeet.omobackend.domain.omakase.Omakase;
import com.depromeet.omobackend.domain.user.User;
import com.depromeet.omobackend.dto.response.OmakaseDto;
import com.depromeet.omobackend.dto.response.ProfileResponse;
import com.depromeet.omobackend.dto.response.UserDto;
import com.depromeet.omobackend.exception.UserNotFoundException;
import com.depromeet.omobackend.repository.refresh.RefreshTokenRepository;
import com.depromeet.omobackend.repository.user.UserRepository;
import com.depromeet.omobackend.util.AuthenticationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    private final AuthenticationUtil authenticationUtil;

    @Override
    public void deleteAccount() {
        User user = authenticationUtil.getUser();
        userRepository.delete(user);
    }

    @Override
    public void logout() {
        refreshTokenRepository.deleteById(authenticationUtil.getUserEmail());
    }

    @Override
    public void modifyNickname(String nickname) {
        User user = authenticationUtil.getUser();
        user.modifyNickname(nickname);
    }

    @Override
    public ProfileResponse getProfile(String email) {
        User user;
        if (email == null) user = authenticationUtil.getUser();
        else user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);

        return ProfileResponse.builder()
                .user(new UserDto(user.getNickname(), user.getProfileImage()))
                .omakaseCount((long) user.getStamps().size())
                .omakases(user.getStamps().stream()
                .map(stamp -> {
                    Omakase omakase = stamp.getOmakase();
                    return OmakaseDto.builder()
                                    .id(omakase.getId())
                                    .name(omakase.getName())
                                    .photoUrl(omakase.getPhotoUrl())
                                    .category(omakase.getCategory().toString())
                                    .country(omakase.getCountry())
                                    .createDate(stamp.getCreatedDate())
                                    .isCertified(stamp.getIsCertified())
                                    .build();
                }).collect(Collectors.toList()))
                .build();
    }

}
