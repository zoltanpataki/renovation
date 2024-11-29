package com.renovation.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileReaderService {
    String getFileContent(MultipartFile file);
}
