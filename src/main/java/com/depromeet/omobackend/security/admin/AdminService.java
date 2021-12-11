package com.depromeet.omobackend.security.admin;

import com.depromeet.omobackend.dto.request.OmakaseRegistrationRequest;
import org.springframework.web.multipart.MultipartFile;

public interface AdminService {
    void registrationOmakase(OmakaseRegistrationRequest request, MultipartFile file);
}
