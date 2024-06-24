package com.swen90014.paplatypusbackend.controller;

import com.swen90014.paplatypusbackend.controller.utils.ResultUtil;
import com.swen90014.paplatypusbackend.service.impl.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/email")
public class EmailController {

    private final EmailService emailService;
    @Autowired
    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }


    @GetMapping("/sendAll/{course_id}")
    public ResultUtil sendEmailWithAttachment(@PathVariable("course_id") Long course_id) {
        CompletableFuture<Boolean> future = emailService.sendToAllStudent(course_id);
        return new ResultUtil(200, null, "SUCCESS");
    }
    @GetMapping("/sendIndividual/{course_id}/{user_id}")
    public ResultUtil sendEmailWithAttachmentIndividual(@PathVariable("course_id") Long course_id, @PathVariable("user_id") Long user_id) {
        CompletableFuture<Boolean> future = emailService.sendToIndividualStudent(course_id, user_id);
        return new ResultUtil(200, null, "SUCCESS");
    }
}
