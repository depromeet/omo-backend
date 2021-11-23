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
import com.depromeet.omobackend.repository.ranking.RankingRepository;
import com.depromeet.omobackend.repository.refresh.RefreshTokenRepository;
import com.depromeet.omobackend.repository.stamp.StampRepository;
import com.depromeet.omobackend.repository.user.UserRepository;
import com.depromeet.omobackend.security.jwt.JwtTokenProvider;
import com.depromeet.omobackend.util.AuthenticationUtil;
import com.depromeet.omobackend.util.ImageUploadUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    @Value("${jwt.exp.refresh}")
    private long refreshExp;
    @Value("${profile.upload.directory}")
    public String profileUploadPath;

    public static final String CONTENT_TYPE = "Content-type";
    private final UserRepository userRepository;
    private final StampRepository stampRepository;
    private final AuthenticationUtil authenticationUtil;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final RankingRepository rankingRepository;

    @Override
    @Transactional
    public UserSaveResponseDto saveAccount(UserSaveRequestDto requestDto, MultipartFile multipartFile) throws IOException {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        String email = requestDto.getEmail();
        String savedName = ImageUploadUtil.uploadFile(email, profileUploadPath, fileName, multipartFile.getBytes());

        requestDto.setProfileUrl(savedName);
        userRepository.save(requestDto.toEntity());

        String access = jwtTokenProvider.generateAccessToken(email);
        String refresh = jwtTokenProvider.generateRefreshToken(email);
        refreshTokenRepository.save(new RefreshToken(email, refresh, refreshExp*60));

        return UserSaveResponseDto.builder()
                .email(email)
                .profileUrl(savedName)
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

    @Override
    public void modifyProfile(MultipartFile multipartFile) throws IOException {
        User user = authenticationUtil.getUser();
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        String savedName = ImageUploadUtil.uploadFile(user.getEmail(), profileUploadPath, fileName, multipartFile.getBytes());
        userRepository.save(user.modifyProfile(savedName));
    }

    @Override
    public void updateLastStampDate(LocalDate lastStampDate) {
        User user = authenticationUtil.getUser();
        LocalDate userCurrentStampDate = user.getLastStampDate();
        user.lastStampDateUpdate(userCurrentStampDate, lastStampDate);
    }

    @Transactional(readOnly = true)
    @Override
    public UserInfoResponse getUserInfo(String email) {
        User user = getUser(email);
        Integer stampCount = stampRepository.findAllByUserAndIsCertifiedTrue(user).size();

        return UserInfoResponse.builder()
                .nickname(user.getNickname())
                .profileUrl(profileUploadPath + user.getProfileUrl())
                .stampCount(stampCount)
                .ranking(rankingRepository.getRankersMoreThanUserStamp(stampCount, user.getLastStampDate(), user.getNickname()) + 1)
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

    @Override
    public ResponseEntity<byte[]> getProfileView(String email) throws IOException {
        InputStream in = null;
        ResponseEntity<byte[]> entity;
        User user = getUser(email);
        try {
            HttpHeaders headers = new HttpHeaders();
            Path uploadPath = Paths.get(profileUploadPath);
            String fileName = user.getProfileUrl();

            File file = new File(uploadPath+"\\"+fileName);
            headers.add(CONTENT_TYPE, Files.probeContentType(file.toPath()));
            in = new FileInputStream(uploadPath+"\\"+fileName);
            entity = new ResponseEntity<byte[]>(IOUtils.toByteArray(in), headers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } finally {
            in.close();
        }
        return entity;
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
