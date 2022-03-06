package com.meesho.notificationservice;

import com.meesho.notificationservice.constants.Constants;
import com.meesho.notificationservice.models.SMS.Message;
import com.meesho.notificationservice.repositories.MessageRepository;
import com.meesho.notificationservice.services.MessageSenderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;


@RunWith(MockitoJUnitRunner.class)
public class MessageSenderServiceTests {
    @InjectMocks
    private MessageSenderService messageSenderService;

    @Mock
    private MessageRepository messageRepository;

    @Test
    public void getMessageByIdTest() throws Exception {
        Mockito.when(messageRepository.findById(1L)).thenReturn(Optional.of(
                new Message(
                1L, "hi", "9811111111",
                Constants.MESSAGE_STATUS.MESSAGE_SENDING_INITIALISED.toString(),
                LocalDateTime.now(),LocalDateTime.now())
        ));
        Optional<Message> message = messageSenderService.getMessageById(1L);
        assertThat(message.get().getText()).isSameAs("hi");
        assertThat(message.get().getPhoneNumber()).isSameAs("9811111111");
        verify(messageRepository).findById(1L);
    }
}
