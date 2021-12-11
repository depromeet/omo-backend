package com.depromeet.omobackend.util;

import com.depromeet.omobackend.exception.FileIsEmptyException;
import com.depromeet.omobackend.exception.FileUploadFailedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

@Component
@Slf4j
public class ImageUploadUtil {
    public static final String MD_5 = "MD5";
    public static final String UTF_8 = "UTF-8";

    @Value("${omakase.image.directory}")
    private String omakaseDirectory;

    public static String uploadFile(String email, String imageUploadPath, String originalFilename,
                                    byte[] fileData) throws IOException {

        Path uploadPath = Paths.get(imageUploadPath);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
            log.debug("Files create Directories {} : ", uploadPath);
        }

        String savedName = getHashingFileName(email, originalFilename);
        File target = new File(String.valueOf(uploadPath), savedName);
        FileCopyUtils.copy(fileData, target);
        log.debug("Files {} copy : ", savedName);

        // 파일 권한 적용
        setFilePermission(target);
        return savedName;
    }

    public String uploadOmakaseImage(MultipartFile file) {
        if(file == null || file.isEmpty() || file.getOriginalFilename() == null) {
            throw new FileIsEmptyException();
        }

        String extension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        String fileName = omakaseDirectory + "/" + UUID.randomUUID() + extension;

        try {
            File temporaryFile = new File(UUID.randomUUID() + extension);
            file.transferTo(temporaryFile);
        } catch (IOException e) {
            e.printStackTrace();
            throw new FileUploadFailedException();
        }

        return fileName;
    }

    private static void setFilePermission(File target) throws IOException {
        Runtime.getRuntime().exec("chmod -R 777 " + target);
        target.setExecutable(true, false);
        target.setReadable(true, false);
        target.setWritable(true, false);
        log.debug("Files saved completed ");
    }

    private static String getHashingFileName(String email, String fileName) {
        if (fileName == null) {
            throw new IllegalArgumentException();
        }
        try {
            MessageDigest md = MessageDigest.getInstance(MD_5);
            md.update(email.getBytes(UTF_8), 0, email.length());
            return new BigInteger(1, md.digest()).toString(16) + fileName;
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            return fileName;
        }
    }
}
