package com.depromeet.omobackend.dto.request;

import com.depromeet.omobackend.domain.omakase.Omakase;
import com.depromeet.omobackend.domain.stamp.Stamp;
import com.depromeet.omobackend.domain.user.User;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StampSaveRequestDto {

    private String fileUrl;
    private User user;
    private Omakase omakase;

    @Builder
    public StampSaveRequestDto(String fileUrl, User user, Omakase omakase) {
        this.fileUrl = fileUrl;
        this.user = user;
        this.omakase = omakase;
    }

    public Stamp toEntity() {
        return Stamp.builder()
                .fileUrl(fileUrl)
                .user(user)
                .omakase(omakase)
                .build();
    }
}
