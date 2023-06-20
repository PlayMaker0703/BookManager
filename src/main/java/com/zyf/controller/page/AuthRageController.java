package com.zyf.controller.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("page/auth")
public class AuthRageController {
//    @Autowired
//    UserMapper mapper;

//    @RequestMapping("/index")
//    public String index(HttpSession session, Model model) {
//
//        AuthUser user = (AuthUser) session.getAttribute("user");
//        if (user == null) {
//            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//            user=mapper.getPasswordByUsername(authentication.getName());
//            session.setAttribute("user",user);
//        }
//        model.addAttribute("user", user);
//        return "user/index";
//    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/register")
    public String register() {
        return "register";
    }
}
