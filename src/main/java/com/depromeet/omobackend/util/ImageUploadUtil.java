package com.depromeet.omobackend.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Slf4j
public class ImageUploadUtil {
    public static final String MD_5 = "MD5";
    public static final String UTF_8 = "UTF-8";

    public static String uploadFile(String email, String profileUploadPath, String originalFilename,
                                    byte[] fileData) throws IOException {

        Path uploadPath = Paths.get(profileUploadPath);

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
