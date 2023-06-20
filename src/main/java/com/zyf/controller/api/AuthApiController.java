package com.zyf.controller.api;

import com.zyf.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/api/auth")
public class AuthApiController {
    @Autowired
    AuthService service;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(@RequestParam("username") String name,
                           @RequestParam("sex") String sex,
                           @RequestParam("grade") String grade,
                           @RequestParam("password") String password) {
        service.register(name, sex, grade, password);
        return "redirect:/login";
    }
}
