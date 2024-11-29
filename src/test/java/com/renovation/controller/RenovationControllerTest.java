package com.renovation.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.renovation.model.RoomResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.FileInputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class RenovationControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    public void whenCalculateTotalEndpointCalledThen__Success() throws Exception {
        //GIVEN
        MockMultipartFile file = new MockMultipartFile("file", new FileInputStream("src/main/resources/file.txt"));

        //WHEN
        MvcResult result = this.mockMvc
                .perform(multipart("/renovation/calculate-total").file(file))
                .andExpect(status().isOk())
                .andReturn();

        //THEN
        String actual = result.getResponse().getContentAsString();
        assertEquals("1592486", actual);
    }

    @Test
    public void whenGetCubicRoomsEndpointCalledThen__Success() throws Exception {
        //GIVEN
        MockMultipartFile file = new MockMultipartFile("file", new FileInputStream("src/main/resources/file.txt"));

        //WHEN
        MvcResult result = this.mockMvc
                .perform(multipart("/renovation/cubic-rooms").file(file))
                .andExpect(status().isOk())
                .andReturn();

        //THEN
        String actual = result.getResponse().getContentAsString();
        List<RoomResponse> mappedResponse = objectMapper.readValue(actual, new TypeReference<>() {});

        assertEquals(5, mappedResponse.size());
        assertEquals(28, mappedResponse.getFirst().length());
        assertEquals(28, mappedResponse.getFirst().width());
        assertEquals(28, mappedResponse.getFirst().height());
        assertEquals(7, mappedResponse.getLast().length());
        assertEquals(7, mappedResponse.getLast().width());
        assertEquals(7, mappedResponse.getLast().height());
    }

    @Test
    public void whenGetRepeatingRoomsEndpointCalledThen__Success() throws Exception {
        //GIVEN
        MockMultipartFile file = new MockMultipartFile("file", new FileInputStream("src/main/resources/file.txt"));

        //WHEN
        MvcResult result = this.mockMvc
                .perform(multipart("/renovation/repeating-rooms").file(file))
                .andExpect(status().isOk())
                .andReturn();

        //THEN
        String actual = result.getResponse().getContentAsString();
        List<RoomResponse> mappedResponse = objectMapper.readValue(actual, new TypeReference<>() {});

        assertEquals(13, mappedResponse.size());
        assertEquals(22, mappedResponse.getFirst().length());
        assertEquals(3, mappedResponse.getFirst().width());
        assertEquals(1, mappedResponse.getFirst().height());
        assertEquals(2, mappedResponse.getLast().length());
        assertEquals(25, mappedResponse.getLast().width());
        assertEquals(8, mappedResponse.getLast().height());
    }
}
