package com.renovation.configuration;

import com.renovation.service.FileReaderService;
import com.renovation.service.FileReaderServiceImpl;
import com.renovation.service.RoomDimensionService;
import com.renovation.service.RoomDimensionServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfig {

    @Bean
    FileReaderService fileReaderService() {
        return new FileReaderServiceImpl();
    }

    @Bean
    RoomDimensionService roomDimensionService() {
        return new RoomDimensionServiceImpl();
    }
}
