package com.renovation.service;

import com.renovation.exception.FileReadingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FileReaderServiceImplTest {

    FileReaderService fileReaderService;
    @Mock
    MultipartFile multipartFile;

    @BeforeEach
    void setup() {
        fileReaderService = new FileReaderServiceImpl();
    }

    @Test
    void whenGetFileContentCalledThenFileConvertedToString__Success() throws IOException {
        //GIVEN
        MultipartFile file = new MockMultipartFile("file", new FileInputStream("src/main/resources/file.txt"));

        //WHEN
        String actual = fileReaderService.getFileContent(file);

        //THEN
        assertNotNull(actual);
        assertEquals(TestConstants.FILE_DATA.getValue(), actual);
    }

    @Test
    void whenGetFileContentCalledThenFileNotFoundExceptionIsThrown__Error() throws IOException {
        //GIVEN
        when(multipartFile.getInputStream()).thenThrow(FileNotFoundException.class);

        //THEN
        assertThrows(FileReadingException.class, () -> fileReaderService.getFileContent(multipartFile));
    }
}
