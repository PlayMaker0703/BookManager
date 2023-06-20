package com.zyf.controller.page;

import com.zyf.service.AuthService;
import com.zyf.service.BookService;
import com.zyf.service.StatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller()
@RequestMapping("page/admin")
public class AdminPageController {
    @Autowired
    AuthService authService;

    @Autowired
    BookService bookService;

    @Autowired
    StatService statService;


    @RequestMapping("/index")
    public String index(HttpSession session, Model model){
        model.addAttribute("user",authService.findUser(session));
        model.addAttribute("borrowList",bookService.getBorrowDetailsList());
        model.addAttribute("globalStat",statService.getGlobalStat());
        return "/admin/index";
    }

    @RequestMapping("/book")
    public String book(HttpSession session, Model model){
        model.addAttribute("user",authService.findUser(session));
        model.addAttribute("bookList",bookService.getAllBook());
        return "/admin/book";
    }

    @RequestMapping("/add-book")
    public String addBook(HttpSession session, Model model){
        model.addAttribute("user",authService.findUser(session));
        return "/admin/add-book";
    }
}
