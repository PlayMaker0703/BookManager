package com.zyf.controller.api;

import com.zyf.entity.AuthUser;
import com.zyf.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
@RequestMapping("/api/user")
public class UserApiController {
    @Autowired
    BookService bookService;

    @RequestMapping(value = "/borrow-book", method = RequestMethod.GET)
    public String borrowBook(@RequestParam("bid") int bid,
                             @SessionAttribute("user") AuthUser authUser) {
        bookService.borrowBook(bid,authUser.getId() );
        return "redirect:/page/user/book";
    }

    @RequestMapping(value = "/return-book",method = RequestMethod.GET)
    public String returnBook(@RequestParam("bid") int bid,
                             @SessionAttribute("user") AuthUser authUser){
        bookService.returnBook(bid,authUser.getId());
        return "redirect:/page/user/index";
    }

}
