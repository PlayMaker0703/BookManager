package com.zyf.controller.page;

import com.zyf.entity.AuthUser;
import com.zyf.service.AuthService;
import com.zyf.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller()
@RequestMapping("page/user")
public class UserPageController {
    @Autowired
    AuthService authService;

    @Autowired
    BookService bookService;


    @RequestMapping("/index")
    public String index(HttpSession session, Model model){
        model.addAttribute("user",authService.findUser(session));
        model.addAttribute("bookList",bookService.getAllBookWithOutBorrow());
        return "/user/index";
    }

    @RequestMapping("/book")
    public String book(HttpSession session, Model model){
        AuthUser user = authService.findUser(session);
        model.addAttribute("user",user);
        model.addAttribute("bookList",bookService.getAllBorrowedById(user.getId()));
        return "/user/book";
    }
}
