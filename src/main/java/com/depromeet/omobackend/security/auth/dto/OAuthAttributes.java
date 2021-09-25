//package com.depromeet.omobackend.security.auth.dto;
//
//import com.depromeet.omobackend.domain.user.Role;
//import com.depromeet.omobackend.domain.user.User;
//import lombok.Builder;
//import lombok.Getter;
//
//import java.util.Map;
//
//
//@Getter
//public class OAuthAttributes {
//    private Map<String, Object> attributes;
//    private String nameAttributeKey;
//    private String nickname;
//    private String email;
//    private String profileImage;
//
//    @Builder
//    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String nickname,
//                           String email, String profileImage) {
//        this.attributes = attributes;
//        this.nameAttributeKey = nameAttributeKey;
//        this.nickname = nickname;
//        this.email = email;
//        this.profileImage = profileImage;
//    }
//
//    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
//        return ofNaver("id", attributes);
//    }
//
//    private static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes) {
//        Map<String, Object> response = (Map<String, Object>) attributes.get("response");
//        return OAuthAttributes.builder()
//                .nickname((String) response.get("nickname"))
//                .email((String) response.get("email"))
//                .profileImage((String) response.get("profile_image"))
//                .attributes(response)
//                .nameAttributeKey(userNameAttributeName)
//                .build();
//    }
//
//    public User toEntity() {
//        return User.builder()
//                .nickname(nickname)
//                .email(email)
//                .profileImage(profileImage)
//                .role((Role.USER))
//                .build();
//    }
//}
