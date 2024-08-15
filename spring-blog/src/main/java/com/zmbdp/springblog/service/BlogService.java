package com.zmbdp.springblog.service;

import com.zmbdp.springblog.mapper.BlogInfoMapper;
import com.zmbdp.springblog.model.BlogInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class BlogService {

    @Autowired
    private BlogInfoMapper blogInfoMapper;

    public List<BlogInfo> getList() {
        return blogInfoMapper.selectAllBlog();
    }

    public BlogInfo getBlogDetail(Integer id) {
        return blogInfoMapper.selectBlogByBlogId(id);
    }

    public Boolean delete(Integer id) {
        return blogInfoMapper.deleteBlog(id) >= 1;
    }

    public Integer insertBlog(BlogInfo blogInfo) {
        if (blogInfo == null || blogInfo.getUserId() < 1) {
            return 0;
        }
        Integer result = 0;
        try {
            result = blogInfoMapper.insertBlog(blogInfo);
        } catch (Exception e) {
            log.error("插入博客错误");
        }
        return result;
    }

    public Boolean update(String title, String content, Integer blogId) {
        Integer num = 0;
        BlogInfo blogInfo = new BlogInfo();
        blogInfo.setTitle(title);
        blogInfo.setContent(content);
        blogInfo.setId(blogId);
        try {
            num = blogInfoMapper.updateBlog(blogInfo);
        } catch (Exception e) {
            return false;
        }
        if (num < 1) {
            System.err.println("为 0 打印的");
            return false;
        }
        return true;
    }
}
