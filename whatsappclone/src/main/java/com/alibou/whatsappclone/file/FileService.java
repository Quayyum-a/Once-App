package com.alibou.whatsappclone.file;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
@RequiredArgsConstructor
public class FileService {

    @Value("application.file.uploads.media-output.path")
    private String mediaOutputPath;`
    public static String saveFile(MultipartFile file, String senderId) {
        return null;
    }
}
