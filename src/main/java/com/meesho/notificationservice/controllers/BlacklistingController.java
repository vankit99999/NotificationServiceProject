package com.meesho.notificationservice.controllers;

import com.meesho.notificationservice.models.BlacklistedNumber;
import com.meesho.notificationservice.models.Message;
import com.meesho.notificationservice.services.BlacklistingService;
import com.meesho.notificationservice.services.MessageSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

import static com.meesho.notificationservice.constants.Constants.MESSAGE_SEND_INIT;

@RestController
@RequestMapping(path = "v1/blacklist")
public class BlacklistingController {

    private final BlacklistingService blacklistingService;

    @Autowired
    public BlacklistingController(BlacklistingService blacklistingService) {
        this.blacklistingService = blacklistingService;
    }

    @PostMapping(path = "/add")
    public String addPhoneNumberToBlacklist(@RequestBody BlacklistedNumber blacklistedNumber) {
        blacklistingService.addPhoneNumberToBlacklist(blacklistedNumber);
        return "Done";
    }

//    @PostMapping(path = "/send")
//    public @ResponseBody String sendMessage(@RequestBody Message message) {
//        messageService.updateDataBase(message);
//        return "done";
//    }

    @GetMapping(path = "/all")
    public List<BlacklistedNumber> getAllBlacklistedNumbers() {
        return blacklistingService.getAllBlacklistedNumbers();
    }

    @PostMapping(path = "/delete")
    public String deletePhoneNumberFromList(@RequestBody BlacklistedNumber blacklistedNumber) {
        blacklistingService.deletePhoneNumberFromList(blacklistedNumber);
        return "deleted";
    }
}