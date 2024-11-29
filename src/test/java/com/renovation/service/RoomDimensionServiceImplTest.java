package com.renovation.service;

import com.renovation.exception.WrongInputException;
import com.renovation.model.RoomResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
@Slf4j
public class RoomDimensionServiceImplTest {
    private static final String LINE_BREAK = "\n";

    RoomDimensionService roomDimensionService;

    @BeforeEach
    void setup() {
        roomDimensionService = new RoomDimensionServiceImpl();
    }

    @Test
    void whenGetTotalAmountOfWallpaperCalledThenTheRightAmountRetrieved__Success() throws IOException {
        //GIVEN
        MultipartFile file = new MockMultipartFile("file", new FileInputStream("src/main/resources/file.txt"));
        List<String> fileContent = getFileContent(file);

        //WHEN
        assert fileContent != null;
        int actual = roomDimensionService.getTotalWallpaperAmount(fileContent);

        //THEN
        assertEquals(1592486, actual);
    }

    @Test
    void whenGetTotalAmountOfWallpaperCalledAndDimensionIsNotNumberThenWrongInputExceptionIsThrown__Error() throws IOException {
        //GIVEN
        MultipartFile file = new MockMultipartFile("file", new FileInputStream("src/main/resources/fileNumberFormatError.txt"));
        List<String> fileContent = getFileContent(file);

        //THEN
        assertThrows(WrongInputException.class, () -> {
            assert fileContent != null;
            roomDimensionService.getTotalWallpaperAmount(fileContent);
        });
    }

    @Test
    void whenGetTotalAmountOfWallpaperCalledAndDimensionIsMissingThenWrongInputExceptionIsThrown__Error() throws IOException {
        //GIVEN
        MultipartFile file = new MockMultipartFile("file", new FileInputStream("src/main/resources/fileMissingDimension.txt"));
        List<String> fileContent = getFileContent(file);

        //THEN
        assertThrows(WrongInputException.class, () -> {
            assert fileContent != null;
            roomDimensionService.getTotalWallpaperAmount(fileContent);
        });
    }

    @Test
    void whenGetCubicRoomsCalledThenTheRoomsRetrievedInDescendingOrder__Success() throws IOException {
        //GIVEN
        MultipartFile file = new MockMultipartFile("file", new FileInputStream("src/main/resources/file.txt"));
        List<String> fileContent = getFileContent(file);

        //WHEN
        assert fileContent != null;
        List<RoomResponse> actual = roomDimensionService.getCubicRooms(fileContent);

        //THEN
        assertEquals(5, actual.size());
        assertEquals(28, actual.getFirst().length());
        assertEquals(28, actual.getFirst().width());
        assertEquals(28, actual.getFirst().height());
        assertEquals(7, actual.getLast().length());
        assertEquals(7, actual.getLast().width());
        assertEquals(7, actual.getLast().height());
    }

    @Test
    void whenGetCubicRoomsCalledAndDimensionIsNotNumberThenWrongInputExceptionIsThrown__Error() throws IOException {
        //GIVEN
        MultipartFile file = new MockMultipartFile("file", new FileInputStream("src/main/resources/fileNumberFormatError.txt"));
        List<String> fileContent = getFileContent(file);

        //THEN
        assertThrows(WrongInputException.class, () -> {
            assert fileContent != null;
            roomDimensionService.getCubicRooms(fileContent);
        });
    }

    @Test
    void whenGetCubicRoomsCalledAndDimensionIsMissingThenWrongInputExceptionIsThrown__Error() throws IOException {
        //GIVEN
        MultipartFile file = new MockMultipartFile("file", new FileInputStream("src/main/resources/fileMissingDimension.txt"));
        List<String> fileContent = getFileContent(file);

        //THEN
        assertThrows(WrongInputException.class, () -> {
            assert fileContent != null;
            roomDimensionService.getCubicRooms(fileContent);
        });
    }

    @Test
    void whenGetRepeatingRoomsCalledThenTheRoomsRetrieved__Success() throws IOException {
        //GIVEN
        MultipartFile file = new MockMultipartFile("file", new FileInputStream("src/main/resources/file.txt"));
        List<String> fileContent = getFileContent(file);

        //WHEN
        assert fileContent != null;
        List<RoomResponse> actual = roomDimensionService.getRepeatingRooms(fileContent);

        //THEN
        assertEquals(13, actual.size());
        assertEquals(22, actual.getFirst().length());
        assertEquals(3, actual.getFirst().width());
        assertEquals(1, actual.getFirst().height());
        assertEquals(2, actual.getLast().length());
        assertEquals(25, actual.getLast().width());
        assertEquals(8, actual.getLast().height());
    }

    private List<String> getFileContent(MultipartFile file) {
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
        return null;
    }

    private List<String> readFromInputStream(InputStream inputStream)
            throws IOException {
        StringBuilder resultStringBuilder = new StringBuilder();
        try (BufferedReader br
                     = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                resultStringBuilder.append(line).append("\n");
            }
        }
        return Arrays.asList(resultStringBuilder.toString().split(LINE_BREAK));
    }
}
