package com.depromeet.omobackend.service.user;

import com.depromeet.omobackend.domain.omakase.Omakase;
import com.depromeet.omobackend.domain.user.User;
import com.depromeet.omobackend.dto.response.MyOmakasesResponse;
import com.depromeet.omobackend.dto.response.OmakasesDto;
import com.depromeet.omobackend.dto.response.UserInfoResponse;
import com.depromeet.omobackend.dto.response.UserDto;
import com.depromeet.omobackend.exception.UserNicknameAlreadyExistsException;
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

//    @Override
//    @Transactional
//    public User saveAccount(com.depromeet.omobackend.dto.user.UserDto userDto) {
//        return userRepository.save(userDto.toEntity());
//    }

    @Override
    public void checkNicknameDuplicate(String nickname) {
        checkNickname(nickname);
    }

    @Override
    @Transactional
    public void deleteAccount() {
        User user = authenticationUtil.getUser();
        userRepository.delete(user);
    }

    @Override
    public void modifyNickname(String nickname) {
        checkNickname(nickname);
        User user = authenticationUtil.getUser();
        userRepository.save(user.modifyNickname(nickname));
    }

    @Transactional(readOnly = true)
    @Override
    public UserInfoResponse getUserInfo(String email) {
        User user = getUser(email);
        Integer stampCount = stampRepository.findAllByUserAndIsCertifiedTrue(user).size();

        return new UserInfoResponse(
                        UserDto.builder()
                        .nickname(user.getNickname())
                        .profileUrl(user.getProfileUrl())
                        .stampCount(stampCount)
                        .power(getPower(stampCount))
                        .build()
        );
    }

    @Transactional(readOnly = true)
    @Override
    public MyOmakasesResponse getMyOmakases(String email) {
        User user = getUser(email);

        return new MyOmakasesResponse(
                (stampRepository.findByUserOrderByCreatedDateDesc(user).stream()
                        .map(stamp -> {
                            Omakase omakase = stamp.getOmakase();
                            return OmakasesDto.builder()
                                    .id(omakase.getId())
                                    .name(omakase.getName())
                                    .photoUrl(omakase.getPhotoUrl())
                                    .createDate(stamp.getCreatedDate())
                                    .isCertified(stamp.getIsCertified())
                                    .build();
                        }).collect(Collectors.toList()))
        );
    }

    private User getUser(String email) {
        if (email != null) return authenticationUtil.getUser(email);
        else return authenticationUtil.getUser();
    }

    private void checkNickname(String nickname) {
        userRepository.findByNickname(nickname)
                .ifPresent(user -> {
                    throw new UserNicknameAlreadyExistsException();
                });
    }

    private Integer getPower(Integer stampCount) {
        if (stampCount < 2) return stampCount;
        else if (stampCount <= 4) return 2;
        else if (stampCount <= 9) return 3;
        else if (stampCount <= 19) return 4;
        else return 5;
    }

}
