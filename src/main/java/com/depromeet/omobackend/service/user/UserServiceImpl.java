package com.depromeet.omobackend.service.user;

import com.depromeet.omobackend.domain.omakase.Omakase;
import com.depromeet.omobackend.domain.refreshtoken.RefreshToken;
import com.depromeet.omobackend.domain.user.User;
import com.depromeet.omobackend.dto.request.UserSaveRequestDto;
import com.depromeet.omobackend.dto.response.MyOmakasesResponse;
import com.depromeet.omobackend.dto.response.OmakasesDto;
import com.depromeet.omobackend.dto.response.UserInfoResponse;
import com.depromeet.omobackend.dto.response.UserSaveResponseDto;
import com.depromeet.omobackend.exception.UserNicknameAlreadyExistsException;
import com.depromeet.omobackend.repository.refresh.RefreshTokenRepository;
import com.depromeet.omobackend.repository.stamp.StampRepository;
import com.depromeet.omobackend.repository.user.UserRepository;
import com.depromeet.omobackend.security.jwt.JwtTokenProvider;
import com.depromeet.omobackend.util.AuthenticationUtil;
import com.depromeet.omobackend.util.ImageUploadUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    @Value("${jwt.exp.refresh}")
    private long refreshExp;

    public static final String MD_5 = "MD5";
    public static final String UTF_8 = "UTF-8";

    private final UserRepository userRepository;
    private final StampRepository stampRepository;
    private final AuthenticationUtil authenticationUtil;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${profile.upload.directory}")
    private String profileUploadPath;

    @Override
    @Transactional
    public UserSaveResponseDto saveAccount(UserSaveRequestDto requestDto, MultipartFile multipartFile) throws IOException {
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        String email = requestDto.getEmail();
        String hashFileName = getHashingFileName(email, fileName);
        requestDto.setProfileUrl(hashFileName);
        User savedUser = userRepository.save(requestDto.toEntity());

        String uploadDir = profileUploadPath + savedUser.getId();
        ImageUploadUtil.saveProfile(uploadDir, fileName, multipartFile);

        String access = jwtTokenProvider.generateAccessToken(email);
        String refresh = jwtTokenProvider.generateRefreshToken(email);
        refreshTokenRepository.save(new RefreshToken(email, refresh, refreshExp*60));

        return UserSaveResponseDto.builder()
                .email(email)
                .access(access)
                .refresh(refresh)
                .build();
    }

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

        return UserInfoResponse.builder()
                .nickname(user.getNickname())
                .profileUrl(user.getProfileUrl())
                .stampCount(stampCount)
                .power(getPower(stampCount))
                .build();
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

    private String getHashingFileName(String email, String fileName) {
        if (fileName == null) {
            throw new IllegalArgumentException();
        }
        try {
            MessageDigest md = MessageDigest.getInstance(MD_5);
            md.update(email.getBytes(UTF_8), 0, email.length());
            return new BigInteger(1, md.digest()).toString(16) + fileName;
        } catch (NoSuchAlgorithmException e) {
            return fileName;
        } catch (UnsupportedEncodingException e) {
            return fileName;
        }
    }

}
