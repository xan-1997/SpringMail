package com.xanlar.controller;

import com.xanlar.config.EmailConfig;
import com.xanlar.entity.Feedback;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class FeedBackController {
    private final EmailConfig emailConfig;

    @PostMapping("feedback")
    public void sendFeedback(@RequestBody @Valid Feedback feedback, BindingResult bindingResult) throws Exception{
            if(bindingResult.hasErrors()){
                throw new Exception("not valid");
            }

        JavaMailSenderImpl mailSender =new JavaMailSenderImpl();
        mailSender.setHost(emailConfig.getHost());
        mailSender.setPort(emailConfig.getPort());
        mailSender.setUsername(emailConfig.getUsername());
        mailSender.setPassword(emailConfig.getPassword());

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(feedback.getEmail());
        message.setTo("xanlar1997070@gmail.com");
        message.setSubject("Feedback from "+feedback.getName());
        message.setText(feedback.getFeedback());

        mailSender.send(message);
    }
}
