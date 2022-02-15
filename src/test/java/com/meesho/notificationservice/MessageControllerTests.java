package com.meesho.notificationservice;

import com.meesho.notificationservice.authentication.JwtUtil;
import com.meesho.notificationservice.authentication.MyUserDetailsService;
import com.meesho.notificationservice.constants.Constants;
import com.meesho.notificationservice.controllers.MessageController;
import com.meesho.notificationservice.models.SMS.Message;
import com.meesho.notificationservice.repositories.BlacklistedNumberRepository;
import com.meesho.notificationservice.repositories.MessageRepository;
import com.meesho.notificationservice.services.MessageSenderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(MessageController.class)
@AutoConfigureMockMvc(addFilters = false)
public class MessageControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MyUserDetailsService myUserDetailsService;

    @MockBean
    private JwtUtil jwtUtil;

    @MockBean
    private MessageSenderService messageSenderService;

    @MockBean
    private MessageRepository messageRepository;

    @MockBean
    private BlacklistedNumberRepository blacklistedNumberRepository;

    @Test
    public void getAllMessagesTest() throws Exception{
        List<Message> messageList = Arrays.asList(
                new Message(1L, "hi", "9811111111",
                        Constants.MESSAGE_STATUS.MESSAGE_SENDING_INITIALISED.toString(),
                        LocalDateTime.now(),LocalDateTime.now()),
                new Message(2L, "hello", "9811111112",
                        Constants.MESSAGE_STATUS.MESSAGE_SENDING_INITIALISED.toString(),
                        LocalDateTime.now(),LocalDateTime.now()));
        when(messageSenderService.getAllMessages()).thenReturn(messageList);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/v1/sms/all"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data",hasSize(2)))
                .andExpect(jsonPath("$.data[0].id").value(1))
                .andExpect(jsonPath("$.data[1].text").value("hello"));
    }

    @Test
    public void getAllMessagesEmptyTest() throws Exception{
        when(messageSenderService.getAllMessages()).thenReturn(Collections.emptyList());
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/v1/sms/all"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}
