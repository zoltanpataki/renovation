package com.renovation.service;

import com.renovation.exception.WrongInputException;
import com.renovation.model.RoomResponse;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ParametersAreNonnullByDefault
public class RoomDimensionServiceImpl implements RoomDimensionService {
    private static final String DIMENSION_DIVIDER = "x";

    public int getTotalWallpaperAmount(List<String> roomDimensions) {
        return roomDimensions
                .stream()
                .map(room -> getNecessaryWallpaperAmount(splitRoomDimensions(room)))
                .reduce(0, Integer::sum);
    }

    @Nonnull
    public List<RoomResponse> getCubicRooms(List<String> roomDimensions) {
        return roomDimensions
                .stream()
                .filter(room -> areRoomDimensionsEqual(splitRoomDimensions(room)))
                .sorted((room1, room2) -> Integer.compare(splitRoomDimensions(room1)[0], splitRoomDimensions(room2)[0]))
                .toList()
                .reversed()
                .stream()
                .map(this::createRoomResponse)
                .toList();
    }

    @Nonnull
    public List<RoomResponse> getRepeatingRooms(List<String> roomDimensions) {
        return roomDimensions
                .stream()
                .collect(Collectors.groupingBy(roomDimension -> roomDimension, Collectors.counting()))
                .entrySet()
                .stream()
                .filter(entry -> entry.getValue() > 1)
                .map(Map.Entry::getKey)
                .toList()
                .stream()
                .map(this::createRoomResponse)
                .toList();
    }

    private int getNecessaryWallpaperAmount(int[] roomDimensions) {
        if (roomDimensions.length < 3) {
            throw new WrongInputException("The provided input is wrong: " + Arrays.toString(roomDimensions));
        }
        int length = roomDimensions[0];
        int width = roomDimensions[1];
        int height = roomDimensions[2];
        int surface = 2 * length * width + 2 * width * height + 2 * length * height;
        surface += Stream.of(length * width, width * height, length * height)
                .mapToInt(v -> v)
                .min()
                .orElseThrow(NoSuchElementException::new);
        return surface;
    }

    private int[] splitRoomDimensions(String roomDimension) {
        try {
            return Arrays.stream(roomDimension.split(DIMENSION_DIVIDER)).mapToInt(Integer::parseInt).toArray();
        } catch (NumberFormatException e) {
            throw new WrongInputException("The provided input is not number: " + roomDimension);
        }
    }

    private boolean areRoomDimensionsEqual(int[] roomDimensions) {
        if (roomDimensions.length < 3) {
            throw new WrongInputException("The provided input is wrong: " + Arrays.toString(roomDimensions));
        }
        return roomDimensions[0] == roomDimensions[1] && roomDimensions[1] == roomDimensions[2];
    }

    private RoomResponse createRoomResponse(String roomDimensions) {
        int[] dimensions = splitRoomDimensions(roomDimensions);
        return new RoomResponse(dimensions[0], dimensions[1], dimensions[2]);
    }
}
