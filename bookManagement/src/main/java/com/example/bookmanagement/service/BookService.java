package com.example.bookmanagement.service;

import com.example.bookmanagement.enums.BookStatusEnum;
import com.example.bookmanagement.enums.ResultCode;
import com.example.bookmanagement.mapper.BookMapper;
import com.example.bookmanagement.model.BookInfo;
import com.example.bookmanagement.model.PageRequest;
import com.example.bookmanagement.model.PageResult;
import com.example.bookmanagement.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service// 把这个对象存进去
public class BookService {
    @Autowired
    BookMapper bookMapper;

    public Result getBookList(PageRequest pageRequest) {
        PageResult<BookInfo> retBooks = new PageResult<>();
        if (pageRequest.getCurrentPage() < 0 || pageRequest.getPageSize() < 1) {
            return Result.fail("参数错误!!!");
        }
        Integer currentPage = (pageRequest.getCurrentPage() - 1) * pageRequest.getPageSize();
        // 先初始化一个空的
        retBooks.setRecords(new ArrayList<>());
        // 直接把有多少个数据放进去
        retBooks.setTotal(bookMapper.countBooks());
        retBooks.setPageRequest(pageRequest);
        // 怕查询的时候出错
        try {
            for (BookInfo x : bookMapper.selectAllBook(currentPage, pageRequest.getPageSize())) {
                // 直接创造枚举类
                x.setStatusCN(BookStatusEnum.getNameByCode(x.getStatus()).getName());
                retBooks.getRecords().add(x);
            }
        } catch (Exception e) {
            log.error("查询 book 发生错误, e: {}", e);
            return Result.fail(e.getMessage());
        }
        return Result.success(retBooks);
    }

    public String addBook(BookInfo bookInfo) {
        if (
                bookInfo == null || !StringUtils.hasLength(bookInfo.getBookName()) || !StringUtils.hasLength(bookInfo.getAuthor()) ||
                bookInfo.getCount() < 0 || bookInfo.getPrice() == null || !StringUtils.hasLength(bookInfo.getPublish())
        ) {
            return "添加失败!!!请检查参数";
        }
        try {
            if (bookMapper.insertBook(bookInfo) > 0) {
                return "";
            } else {
                return "添加图书失败，请联系管理人员";
            }
        } catch (Exception e) {
            log.error("添加图书出错; e: {}", e);
        }
        return "添加失败!!!请检查参数";
    }

    public BookInfo queryBookInfoById(Integer bookId) {
        if (bookId == null || bookId < 1) {
            log.error("查询错误; bookId: {}", bookId);
            return null;
        }
        BookInfo bookInfo = null;
        try {
            bookInfo = bookMapper.selectBookById(bookId);
            bookInfo.setStatusCN(BookStatusEnum.getNameByCode(bookInfo.getStatus()).getName());
        } catch (Exception e) {
            log.error("查询错误; e: {}", e);
            return null;
        }
        return bookInfo;
    }

    public String updateBook(BookInfo bookInfo) {
        if (bookInfo == null || bookInfo.getBookId() == null || bookInfo.getBookId() < 1) {
            log.error("修改失败!!!");
            return "修改失败!!!";
        }
        int retBook;
        try {
            retBook = bookMapper.updateBook(bookInfo);
        } catch (Exception e) {
            log.error("修改错误; e: {}", e);
            return "修改失败!!!";
        }
        if (retBook < 1){
            log.error("更新失败; bookInfo: {}", bookInfo);
            return "修改失败!!!请联系管理人员";
        }
        return "";
    }

    public String deleteBook(Integer bookId) {
        BookInfo bookInfo = bookMapper.selectBookById(bookId);
        // 使用逻辑删除，把 status 改成0
        if (bookInfo == null || bookId == null || bookId < 1) {
            log.error("删除失败; bookInfo: {}", bookInfo);
            return "删除失败";
        }
        try {
            bookInfo.setStatus(0);
            bookInfo.setStatusCN(BookStatusEnum.getNameByCode(0).getName());
            bookMapper.updateBook(bookInfo);
        } catch (Exception e) {
            log.error("删除失败; bookInfo: {}", bookInfo);
            return "删除失败!!!请联系管理人员";
        }
        // 修改成功返回 ""
        return "";
    }

    public String batchDelete(List<Integer> bookIds) {
        log.error(bookIds.toString());
        try {
            if (bookMapper.batchDelete(bookIds) > 0) {
                return "";
            } else {
                log.error("删除失败!!!; bookIds: {}", bookIds);
                return "删除失败!!!请联系管理人员";
            }
        } catch (Exception e) {
            log.error("删除失败!!!; bookIds: {}", bookIds);
            return "删除失败!!!请联系管理人员";
        }
    }
}
