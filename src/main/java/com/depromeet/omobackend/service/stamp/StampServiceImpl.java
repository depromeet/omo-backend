package com.depromeet.omobackend.service.stamp;

import com.depromeet.omobackend.domain.omakase.Omakase;
import com.depromeet.omobackend.domain.stamp.Stamp;
import com.depromeet.omobackend.domain.user.User;
import com.depromeet.omobackend.dto.request.StampSaveRequestDto;
import com.depromeet.omobackend.dto.request.StampUpdateRequestDto;
import com.depromeet.omobackend.dto.response.StampsCountResponseDto;
import com.depromeet.omobackend.repository.omakase.OmakaseRepository;
import com.depromeet.omobackend.repository.stamp.StampRepository;
import com.depromeet.omobackend.service.user.UserService;
import com.depromeet.omobackend.util.AuthenticationUtil;
import com.depromeet.omobackend.util.ImageUploadUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.time.LocalDate;

@RequiredArgsConstructor
@Service
public class StampServiceImpl implements StampService {

    private final AuthenticationUtil authenticationUtil;
    private final UserService userService;
    private final StampRepository stampRepository;
    private final OmakaseRepository omakaseRepository;

    @Value("${receipt.upload.directory}")
    private String receiptUploadPath;

    /**
     * User 닉네임과 인증된 도장 카운트 개수만 조회할 때 사용
     * @return
     */
    @Override
    public StampsCountResponseDto getStampCount() {
        User user = authenticationUtil.getUser();
        Integer stampCount = stampRepository.findAllByUserAndIsCertifiedTrue(user).size();
        StampsCountResponseDto stampsCountResponseDto = StampsCountResponseDto.builder()
                .nickname(user.getNickname())
                .profileUrl(user.getProfileUrl())
                .stampCount(stampCount)
                .build();
        return stampsCountResponseDto;
    }

    /**
     * 업로드한 레시피 영수증 파일 저장
     * @param multipartFile
     * @return
     * @throws IOException
     */
    @Override
    public String saveReceipt(MultipartFile multipartFile) throws IOException {
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        String uploadDir = receiptUploadPath + fileName;
        ImageUploadUtil.uploadFile(uploadDir, fileName, multipartFile.getBytes());
        return fileName;
    }

    @Override
    @Transactional
    public Stamp saveStamp(Long omakaseId, LocalDate receiptIssuanceDate, StampSaveRequestDto requestDto) {
        Omakase omakase = omakaseRepository.findById(omakaseId)
                .orElseThrow(() -> new IllegalArgumentException("Omakase if Not Found. id=" + omakaseId));

        if (authenticationUtil.getUser() != null) {
            User user = authenticationUtil.getUser();
            requestDto.setReceiptIssuanceDate(receiptIssuanceDate);
            requestDto.setUser(user);
            requestDto.setOmakase(omakase);
        }
        return stampRepository.save(requestDto.toEntity());
    }

    /**
     * 관리자가 도장을 인증하는 메서드
     * @param id
     * @param requestDto
     * @return
     */
    @Override
    @Transactional
    public Long isCertifiedUpdate(Long id, StampUpdateRequestDto requestDto) {
        Stamp stamp = stampRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Stamp is Not Found. id=" + id));
        LocalDate receiptIssuanceDate = requestDto.getReceiptIssuanceDate();

        stamp.update(true, receiptIssuanceDate);
        userService.updateLastStampDate(receiptIssuanceDate);  // User의 lastStampDate Update
        return id;
    }
}
