package com.depromeet.omobackend.util;

import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

public class ImageUploadUtil {

    public static String uploadFile(String profileUploadPath, String originalFilename,
                                    byte[] fileData) throws IOException {
        Path uploadPath = Paths.get(profileUploadPath);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        UUID uid = UUID.randomUUID();
        String savedName = uid + "_" + originalFilename;
        File target = new File(String.valueOf(uploadPath), savedName);
        FileCopyUtils.copy(fileData, target);
        return savedName;
    }
}
