package com.depromeet.omobackend.service.user;

import com.depromeet.omobackend.domain.omakase.Omakase;
import com.depromeet.omobackend.domain.user.User;
import com.depromeet.omobackend.dto.response.OmakaseDto;
import com.depromeet.omobackend.dto.response.MypageResponse;
import com.depromeet.omobackend.dto.response.UserDto;
import com.depromeet.omobackend.exception.UserNotFoundException;
import com.depromeet.omobackend.repository.refresh.RefreshTokenRepository;
import com.depromeet.omobackend.repository.stamp.StampRepository;
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
    private final StampRepository stampRepository;

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
    public MypageResponse getMyPage(String email) {
        User user = authenticationUtil.getUser();

        return MypageResponse.builder()
                .user(
                        UserDto.builder()
                        .nickname(user.getNickname())
                        .profileImage(user.getProfileImage())
                        .omakaseCount((long) user.getStamps().size())
                        .build()
                )
                .omakases(stampRepository.findByUserOrderByCreatedDateDesc(user).stream()
                    .map(stamp -> {
                        Omakase omakase = stamp.getOmakase();
                        return OmakaseDto.builder()
                                    .id(omakase.getId())
                                    .name(omakase.getName())
                                    .photoUrl(omakase.getPhotoUrl())
                                    .country(omakase.getCountry())
                                    .createDate(stamp.getCreatedDate())
                                    .isCertified(stamp.isCertified())
                                    .build();
                    }).collect(Collectors.toList()))
                .build();
    }

}
