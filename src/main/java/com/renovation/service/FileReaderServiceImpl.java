package com.renovation.service;

import com.renovation.exception.FileReadingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@Slf4j
@ParametersAreNonnullByDefault
public class FileReaderServiceImpl implements FileReaderService {
    private static final String LINE_BREAK = "\n";

    @Nonnull
    public String getFileContent(MultipartFile file) {
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
            return readFromInputStream(inputStream);
        } catch (FileNotFoundException fnfe) {
            log.error("The file is not present");
        } catch (IOException ioe) {
            log.error("Error during the file reading");
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    log.error("Error during closing the inputStream");
                }
            }
        }
        throw new FileReadingException("The file reading was not successful");
    }

    @Nonnull
    private String readFromInputStream(InputStream inputStream)
            throws IOException {
        StringBuilder resultStringBuilder = new StringBuilder();
        try (BufferedReader br
                     = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                resultStringBuilder.append(line).append(LINE_BREAK);
            }
        }
        return resultStringBuilder.toString();
    }
}
