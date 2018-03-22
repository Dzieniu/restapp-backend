package com.github.dzieniu2.controller.other;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/logged-out")
public class LogoutController {

    @GetMapping
    public String loggedOut(){
        return "Logged-out";
    }
}
