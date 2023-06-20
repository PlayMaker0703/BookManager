package com.zyf.service.impl;

import com.zyf.entity.Book;
import com.zyf.entity.Borrow;
import com.zyf.entity.BorrowDetails;
import com.zyf.mapper.BookMapper;
import com.zyf.mapper.UserMapper;
import com.zyf.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    BookMapper bookMapper;

    @Autowired
    UserMapper userMapper;

    @Override
    public List<Book> getAllBook() {
        return bookMapper.allBook();
    }

    @Override
    public List<Book> getAllBookWithOutBorrow() {//借阅后用户的书籍界面过滤掉已经借过的书
        List<Book> books = bookMapper.allBook();
        List<Integer> borrows = bookMapper
                .borrowList()
                .stream()
                .map(Borrow::getBid)
                .collect(Collectors.toList());

        return books.stream()
                .filter(book -> !borrows.contains(book.getBid()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Book> getAllBorrowedById(int id) {
        Integer sid = userMapper.getSidByUserId(id);
        if (sid == null) return Collections.EMPTY_LIST;
        return bookMapper.borrowListBySid(sid)
                .stream()
                .map(borrow -> bookMapper.getBookById(borrow.getBid()))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void deleteBook(int bid) {
        bookMapper.deleteBook(bid);
    }

    @Transactional
    @Override
    public void addBook(String title, String description, double price) {
        bookMapper.addBook(title, description, price);
    }

    @Override
    public void borrowBook(int bid, int id) {
        Integer sid = userMapper.getSidByUserId(id);
        if (sid == null) return;
        bookMapper.addBorrow(bid, sid);
    }

    @Override
    public void returnBook(int bid, int id) {
        Integer sid = userMapper.getSidByUserId(id);
        if (sid == null) return;
        bookMapper.deleteBorrow(bid, sid);
    }

    @Override
    public List<BorrowDetails> getBorrowDetailsList() {
        return bookMapper.borrowDetailsList();
    }
}
