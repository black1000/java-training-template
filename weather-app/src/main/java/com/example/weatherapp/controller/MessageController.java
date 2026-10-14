package com.example.weatherapp.controller;

import com.example.weatherapp.model.Message;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller 
public class MessageController {
    @GetMapping("/message")
    public String showMessage(Model model) {
        Message message = new Message();
        message.setText("Hello from Spring Boot!");
        message.setAuthor("test");
        model.addAttribute("message", message);

        return "message";
    }
}
