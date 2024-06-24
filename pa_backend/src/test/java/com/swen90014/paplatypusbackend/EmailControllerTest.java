package com.swen90014.paplatypusbackend;


import com.swen90014.paplatypusbackend.controller.EmailController;
import com.swen90014.paplatypusbackend.service.impl.EmailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.concurrent.CompletableFuture;

import static org.mockito.Mockito.*;

public class EmailControllerTest {

    @Mock
    private EmailService emailService;

    @InjectMocks
    private EmailController emailController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        emailController = new EmailController(emailService);
        mockMvc = MockMvcBuilders.standaloneSetup(emailController).build();
    }

    @Test
    public void testSendEmailWithAttachment_Success() throws Exception {
        when(emailService.sendToAllStudent(anyLong())).thenReturn(CompletableFuture.completedFuture(true));


        mockMvc.perform(MockMvcRequestBuilders.get("/email/sendAll/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.state").value(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("SUCCESS"));

        verify(emailService, times(1)).sendToAllStudent(anyLong());
    }

}
