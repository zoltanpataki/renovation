package com.renovation.controller;

import com.renovation.model.RoomResponse;
import com.renovation.service.FileReaderService;
import com.renovation.service.RoomDimensionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Arrays;
import java.util.List;

@RestController
@Slf4j
@ParametersAreNonnullByDefault
public class RenovationController implements RenovationApi {
    private static final String LINE_BREAK = "\n";

    private final FileReaderService fileReaderService;
    private final RoomDimensionService roomDimensionService;

    @Autowired
    public RenovationController(FileReaderService fileReaderService, RoomDimensionService roomDimensionService) {
        this.fileReaderService = fileReaderService;
        this.roomDimensionService = roomDimensionService;
    }

    @Override
    public ResponseEntity<List<RoomResponse>> getCubicRooms(MultipartFile file) {
        log.debug("Get cubic rooms endpoint called");
        List<String> roomDimensions = readDataFromFile(file);
        List<RoomResponse> cubicRooms = roomDimensionService.getCubicRooms(roomDimensions);
        return ResponseEntity.ok(cubicRooms);
    }

    @Override
    public ResponseEntity<List<RoomResponse>> getRepeatingRooms(MultipartFile file) {
        log.debug("Get repeating rooms endpoint called");
        List<String> roomDimensions = readDataFromFile(file);
        List<RoomResponse> repeatingRooms = roomDimensionService.getRepeatingRooms(roomDimensions);
        return ResponseEntity.ok(repeatingRooms);
    }

    @Override
    public ResponseEntity<Integer> calculateTotalWallpaper(MultipartFile file) {
        log.debug("Get total wallpaper endpoint called");
        List<String> roomDimensions = readDataFromFile(file);
        int result = roomDimensionService.getTotalWallpaperAmount(roomDimensions);
        return ResponseEntity.ok(result);
    }

    private List<String> readDataFromFile(MultipartFile file) {
        String data = fileReaderService.getFileContent(file);
        log.debug("The file successfully converted into: " + data);
        return Arrays.asList(data.split(LINE_BREAK));
    }
}
