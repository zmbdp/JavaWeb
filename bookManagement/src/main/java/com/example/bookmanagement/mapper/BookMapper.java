package com.example.bookmanagement.mapper;

import com.example.bookmanagement.model.BookInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BookMapper {
    @Insert("insert into book_info (book_name, author, count, price, publish, status) " +
            "values (#{bookName}, #{author}, #{count}, #{price}, #{publish}, #{status})")
    Integer insertBook(BookInfo bookInfo);

    @Select("select book_id, book_name, author, count, price, publish, status, create_time, update_time " +
            "from book_info where status <> 0 " +
            "order by book_id asc " +
            "limit #{currentPage}, #{pageSize}")
    List<BookInfo> selectAllBook(@Param("currentPage") Integer currentPage, @Param("pageSize") Integer pageSize);

    @Select("select count(1) from book_info where status <> 0")
    Integer countBooks();

    @Select("select book_id, book_name, author, count, price, publish, status, create_time, update_time " +
            "from book_info where status <> 0 and book_id = #{bookId}")
    BookInfo selectBookById(@Param("bookId") Integer bookId);

    Integer updateBook(BookInfo bookInfo);

    Integer batchDelete(@Param("bookIds") List<Integer> bookIds);
}
