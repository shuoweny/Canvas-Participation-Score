package com.swen90014.paplatypusbackend;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.swen90014.paplatypusbackend.controller.SaveCanvasDataController;
import com.swen90014.paplatypusbackend.controller.utils.ResultUtil;
import com.swen90014.paplatypusbackend.service.impl.CanvasService;
import com.swen90014.paplatypusbackend.service.impl.CourseServiceImpl;
import com.swen90014.paplatypusbackend.service.impl.ScoreServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class SaveCanvasDataControllerTest {

    private final SaveCanvasDataController controllerUnderTest;
    private final CanvasService canvasService;
    private final MockMvc mockMvc;

    public SaveCanvasDataControllerTest() {
        canvasService = Mockito.mock(CanvasService.class);
        CourseServiceImpl courseService = Mockito.mock(CourseServiceImpl.class);
        ScoreServiceImpl scoreService = Mockito.mock(ScoreServiceImpl.class);
        controllerUnderTest = new SaveCanvasDataController(canvasService, scoreService, courseService);
        mockMvc = MockMvcBuilders.standaloneSetup(controllerUnderTest).build();
    }

    @Test
    void testSaveAllDataSuccess() throws Exception {
        when(canvasService.saveData()).thenReturn(null);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/save/all"))
                .andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());

        String content = response.getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        ResultUtil result = objectMapper.readValue(content, ResultUtil.class);

        assertEquals(200, result.getState());
        assertEquals("SUCCESS", result.getMessage());
    }


//    @Test
//    void testChangeSaveFrequencySuccess() throws Exception {
//        Map<String, String> requestPayload = new HashMap<>();
//        requestPayload.put("cron", "0 0 0 * * ?");
//
//        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
//                        .post("/save/frequency")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(new ObjectMapper().writeValueAsString(requestPayload)))
//                .andReturn();
//
//        MockHttpServletResponse response = mvcResult.getResponse();
//        assertEquals(HttpStatus.OK.value(), response.getStatus());
//
//        String content = response.getContentAsString();
//        ObjectMapper objectMapper = new ObjectMapper();
//        ResultUtil result = objectMapper.readValue(content, ResultUtil.class);
//
//        assertEquals(200, result.getState());
//        assertEquals("Cron expression updated", result.getMessage());
//    }

}

