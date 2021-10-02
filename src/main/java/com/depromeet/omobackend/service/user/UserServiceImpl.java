package com.depromeet.omobackend.service.user;

import com.depromeet.omobackend.domain.omakase.Omakase;
import com.depromeet.omobackend.domain.user.User;
import com.depromeet.omobackend.dto.response.MypageResponse;
import com.depromeet.omobackend.dto.response.OmakaseDto;
import com.depromeet.omobackend.dto.response.UserDto;
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
    @Transactional
    public void modifyNickname(String nickname) {
        User user = authenticationUtil.getUser();
        userRepository.save(user.modifyNickname(nickname));
    }

    @Override
    @Transactional(readOnly = true)
    public MypageResponse getMyPage(String email) {
        User user = authenticationUtil.getUser();

        return MypageResponse.builder()
                .user(
                        UserDto.builder()
                        .nickname(user.getNickname())
                        .profileImage(user.getProfileImage())
                        .stampCount(stampRepository.countAllByUserAndCertifiedTrue(user))
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
