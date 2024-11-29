package com.renovation.controller;

import com.renovation.model.RoomResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface RenovationApi {

    @RequestMapping(
            method = RequestMethod.POST,
            value = "/renovation/cubic-rooms",
            produces = { "application/json" },
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    ResponseEntity<List<RoomResponse>> getCubicRooms(
            @RequestParam("file") MultipartFile file
    );


    @RequestMapping(
            method = RequestMethod.POST,
            value = "/renovation/repeating-rooms",
            produces = { "application/json" },
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    ResponseEntity<List<RoomResponse>> getRepeatingRooms(
            @RequestParam("file") MultipartFile file
    );

    @RequestMapping(
            method = RequestMethod.POST,
            value = "/renovation/calculate-total",
            produces = { "application/json" },
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    ResponseEntity<Integer> calculateTotalWallpaper(
            @RequestParam("file") MultipartFile file
    );

}
