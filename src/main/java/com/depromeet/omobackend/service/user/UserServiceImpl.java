package com.depromeet.omobackend.service.user;

import com.depromeet.omobackend.domain.omakase.Omakase;
import com.depromeet.omobackend.domain.stamp.Stamp;
import com.depromeet.omobackend.domain.user.User;
import com.depromeet.omobackend.dto.response.OmakasesDto;
import com.depromeet.omobackend.dto.response.MypageResponse;
import com.depromeet.omobackend.dto.response.UserDto;
import com.depromeet.omobackend.exception.UserNicknameAlreadyExistsException;
import com.depromeet.omobackend.exception.UserNotAuthenticatedException;
import com.depromeet.omobackend.repository.refresh.RefreshTokenRepository;
import com.depromeet.omobackend.repository.stamp.StampRepository;
import com.depromeet.omobackend.repository.user.UserRepository;
import com.depromeet.omobackend.util.AuthenticationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final StampRepository stampRepository;
    private final AuthenticationUtil authenticationUtil;

    @Override
    @Transactional
    public User saveAccount(com.depromeet.omobackend.dto.user.UserDto userDto) {
        return userRepository.save(userDto.toEntity());
    }

    @Override
    @Transactional
    public void deleteAccount() {
        User user = authenticationUtil.getUser();
        userRepository.delete(user);
    }

    @Override
    public void logout() {
        try{
            refreshTokenRepository.deleteAllByEmail(authenticationUtil.getUserEmail());
        } catch(Exception e) {
            throw new UserNotAuthenticatedException();
        }
    }

    @Override
    public void modifyNickname(String nickname) {
        userRepository.findByNickname(nickname)
                .ifPresent(user -> {
                    throw new UserNicknameAlreadyExistsException();
                });
        User user = authenticationUtil.getUser();
        userRepository.save(user.modifyNickname(nickname));
    }

    @Override
    @Transactional(readOnly = true)
    public MypageResponse getMyPage(String email) {
        User user = authenticationUtil.getUser();
        Integer stampCount = stampRepository.findAllByUserAndIsCertifiedTrue(user).size();

        return MypageResponse.builder()
                .user(
                        UserDto.builder()
                        .nickname(user.getNickname())
                        .profileUrl(user.getProfileUrl())
                        .stampCount(stampCount)
                        .power(getPower(stampCount))
                        .build()
                )
                .omakases(stampRepository.findByUserOrderByCreatedDateDesc(user).stream()
                    .map(stamp -> {
                        Omakase omakase = stamp.getOmakase();
                        Stamp recentlyStamp = stampRepository.findFirstByOmakaseOrderByCreatedDateDesc(omakase)
                                .orElse(null);
                        return OmakasesDto.builder()
                                    .id(omakase.getId())
                                    .name(omakase.getName())
                                    .photoUrl(omakase.getPhotoUrl())
                                    .createDate(recentlyStamp == null ? null : recentlyStamp.getCreatedDate())
                                    .isCertified(recentlyStamp == null ? null : recentlyStamp.getIsCertified())
                                    .build();
                    }).collect(Collectors.toList()))
                .build();
    }

    // 변동 가능
    private Integer getPower(Integer stampCount) {
        if (stampCount < 16) {
            return 1;
        } else if(stampCount < 31) {
            return 2;
        } else {
            return 3;
        }
    }

}
