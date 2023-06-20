package com.zyf.mapper;

import com.zyf.entity.Book;
import com.zyf.entity.Borrow;
import com.zyf.entity.BorrowDetails;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BookMapper {
    @Select("select * from book ")
    List<Book> allBook();

    @Select("select * from book where bid=#{bid}")
    Book getBookById(int bid);

    @Delete("delete  from book where bid=#{bid} ")
    void deleteBook(int bid);

    @Insert("insert into book(title,description,price) values(#{title},#{description},#{price}) ")
//添加书
    void addBook(@Param("title") String title, @Param("description") String description, @Param("price") double price);

    @Insert("insert into borrow(bid,sid,`time`) values(#{bid},#{sid},NOW())")
    void addBorrow(@Param("bid") int bid, @Param("sid") int sid);

    @Delete("delete from borrow where bid=#{bid}) and sid=#{sid}")
    void deleteBorrow(@Param("bid") int bid, @Param("sid") int sid);

    @Select("select * from borrow")
    List<Borrow> borrowList();

    @Select("select * from borrow where sid=#{sid}")
    List<Borrow> borrowListBySid(int sid);

    @Results({
            @Result(id = true,column = "id",property = "id"),
            @Result(column = "title",property = "book_title"),
            @Result(column = "name",property = "user_name"),
            @Result(column = "time",property = "time"),
    })
    @Select("SELECT * FROM borrow LEFT JOIN book ON book.bid=borrow.bid LEFT JOIN student ON student.sid=borrow.sid")
    List<BorrowDetails> borrowDetailsList();//联查

    @Select("select count(*) from book")
    int getBookCount();

    @Select("select count(*) from borrow")
    int getBorrowCount();

}
