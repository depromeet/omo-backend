package com.depromeet.omobackend.service.stamp;

import com.depromeet.omobackend.domain.stamp.Stamp;
import com.depromeet.omobackend.domain.user.User;
import com.depromeet.omobackend.dto.request.StampSaveRequestDto;
import com.depromeet.omobackend.dto.request.StampUpdateRequestDto;
import com.depromeet.omobackend.repository.stamp.StampRepository;
import com.depromeet.omobackend.util.AuthenticationUtil;
import com.depromeet.omobackend.util.ImageUploadUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;

@RequiredArgsConstructor
@Service
public class StampServiceImpl implements StampService {

    private final AuthenticationUtil authenticationUtil;
    private final StampRepository stampRepository;

    @Value("${receipt.upload.directory}")
    private String receiptUploadPath;

    @Override
    public String saveReceipt(MultipartFile multipartFile) throws IOException {
        // 업로드한 레시피 영수증 파일 저장
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        String uploadDir = receiptUploadPath + fileName;
        ImageUploadUtil.saveProfile(uploadDir, fileName, multipartFile);
        return fileName;
    }

    @Override
    @Transactional
    public Stamp saveStamp(StampSaveRequestDto requestDto) {
        if (authenticationUtil.getUser() != null) {
            User user = authenticationUtil.getUser();
            requestDto.setUser(user);
        }
        return stampRepository.save(requestDto.toEntity());
    }

    @Override
    @Transactional
    public Long isCertifiedUpdate(Long id, StampUpdateRequestDto requestDto) {
        Stamp stamp = stampRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Stamp is Not Found. id=" + id));
        stamp.update(true);
        return id;
    }
}
