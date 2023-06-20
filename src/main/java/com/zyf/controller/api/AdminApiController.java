package com.zyf.controller.api;

import com.zyf.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/api/admin")
public class AdminApiController {
    @Autowired
    BookService service;


    @RequestMapping(value = "/del-book", method = RequestMethod.GET)
    public String deleteBook(@RequestParam("bid") int bid) {
        service.deleteBook(bid);
        return "redirect:/page/admin/book";
    }

    @RequestMapping(value = "/add-book",method = RequestMethod.POST)
    public String addBook(@RequestParam("title") String title,
                          @RequestParam("description") String description,
                          @RequestParam("price") double price){
        service.addBook(title,description,price);

        return "redirect:/page/admin/book";
    }
}
