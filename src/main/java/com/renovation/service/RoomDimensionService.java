package com.renovation.service;

import com.renovation.model.RoomResponse;

import java.util.List;

public interface RoomDimensionService {
    int getTotalWallpaperAmount(List<String> roomDimensions);
    List<RoomResponse> getCubicRooms(List<String> roomDimensions);
    List<RoomResponse> getRepeatingRooms(List<String> roomDimensions);
}
