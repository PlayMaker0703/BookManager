package com.zyf.service;

import com.zyf.entity.Book;
import com.zyf.entity.BorrowDetails;

import java.util.List;

public interface BookService {
    List<Book> getAllBook();

    List<Book> getAllBookWithOutBorrow();//过滤已经借的书

    List<Book> getAllBorrowedById(int id);

    void deleteBook(int id);

    void addBook(String title,String description,double price);

    void borrowBook(int bid, int id);

    void returnBook(int bid, int id);

    List<BorrowDetails> getBorrowDetailsList();
}
