package com.depromeet.omobackend.security.admin;

import com.depromeet.omobackend.domain.omakase.Category;
import com.depromeet.omobackend.domain.omakase.Holiday;
import com.depromeet.omobackend.domain.omakase.Level;
import com.depromeet.omobackend.domain.omakase.Omakase;
import com.depromeet.omobackend.dto.request.OmakaseRegistrationRequest;
import com.depromeet.omobackend.exception.OmakaseNotFoundException;
import com.depromeet.omobackend.repository.omakase.OmakaseRepository;
import com.depromeet.omobackend.util.ImageUploadUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
public class AdminServiceImpl implements AdminService {

    private final OmakaseRepository omakaseRepository;
    private final ImageUploadUtil imageUploadUtil;

    @Override
    public long registrationOmakase(OmakaseRegistrationRequest request) {
        return omakaseRepository.save(
                Omakase.builder()
                        .address(request.getAddress())
                        .businessHours(request.getBusinessHours())
                        .category(Category.SUSHI)
                        .county(request.getCounty())
                        .description(request.getDescription())
                        .holiday(Holiday.valueOf(request.getHoliday()))
                        .level(Level.valueOf(request.getLevel()))
                        .name(request.getName())
                        .phoneNumber(request.getPhoneNumber())
                        .photoUrl(null)
                        .priceInformation(request.getPriceInformation())
                .build()
        ).getId();
    }

    @Transactional
    @Override
    public void registrationOmakase(Long id, MultipartFile file) {
        String photoUrl = file != null ? imageUploadUtil.uploadOmakaseImage(file) : null;
        Omakase omakase = omakaseRepository.findById(id)
                .orElseThrow(OmakaseNotFoundException::new);
        omakase.updatePhotoUrl(photoUrl);
    }

}
