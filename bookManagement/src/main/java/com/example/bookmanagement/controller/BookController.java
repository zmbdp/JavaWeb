package com.example.bookmanagement.controller;

import com.example.bookmanagement.model.*;
import com.example.bookmanagement.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;


@RestController
@RequestMapping("/book")
public class BookController {
    @Autowired
    private BookService bookService;

    @RequestMapping("/getBookList")
    public Result getBookList(PageRequest pageRequest, HttpSession session) {
        /*// 看看是否登录
        UserInfo userInfo = (UserInfo) session.getAttribute(Constants.SESSION_USER_KEY);
        if (userInfo == null || userInfo.getUserId() < 1 || "".equals(userInfo.getUserName())) {
            return Result.unlogin();
        }*/
        // 获取数据信息
        // 对图书信息进行修改
        // 返回数据
        // mock: 虚拟/假 数据
        return bookService.getBookList(pageRequest);
    }

    @RequestMapping(value = "/addBook", produces = "application/json")
    public String addBook(BookInfo bookInfo) {
        return bookService.addBook(bookInfo);
    }

    @RequestMapping("/queryBookInfoById")
    public BookInfo queryBookInfoById(Integer bookId) {
        return bookService.queryBookInfoById(bookId);
    }

    @RequestMapping(value = "/updateBook", produces = "application/json")
    public String updateBook(BookInfo bookInfo) {
        return bookService.updateBook(bookInfo);
    }

    @RequestMapping(value = "/deleteBook", produces = "application/json")
    public String deleteBook(Integer bookId) {
        return bookService.deleteBook(bookId);
    }

    @RequestMapping(value = "/batchDelete", produces = "application/json")
    public String batchDelete(@RequestParam List<Integer> bookIds) {// 接收 url 上的数据
        System.err.println(bookIds.toString());
        try {
            if (bookService.batchDelete(bookIds).isEmpty()) {
                return ""; // 返回空字符串表示成功
            } else {
                return "删除失败!!!请联系管理人员";
            }
        } catch (Exception e) {
            return "删除失败!!!请联系管理人员"; // 返回失败信息
        }
    }
}
