package com.depromeet.omobackend.security.admin;

import com.depromeet.omobackend.domain.omakase.Category;
import com.depromeet.omobackend.domain.omakase.Omakase;
import com.depromeet.omobackend.dto.request.OmakaseRegistrationRequest;
import com.depromeet.omobackend.repository.omakase.OmakaseRepository;
import com.depromeet.omobackend.util.ImageUploadUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
public class AdminServiceImpl implements AdminService {

    private final OmakaseRepository omakaseRepository;

    @Override
    public void registrationOmakase(OmakaseRegistrationRequest request, MultipartFile file) {
        String photoUrl = file != null ? ImageUploadUtil.uploadOmakaseImage(file) : null;

        omakaseRepository.save(
                Omakase.builder()
                        .address(request.getAddress())
                        .businessHours(request.getBusinessHours())
                        .category(Category.SUSHI)
                        .county(request.getCounty())
                        .description(request.getDescription())
                        .holiday(request.getHoliday())
                        .level(request.getLevel())
                        .name(request.getName())
                        .phoneNumber(request.getPhoneNumber())
                        .photoUrl(photoUrl)
                        .priceInformation(request.getPriceInformation())
                .build()
        );

    }
}
