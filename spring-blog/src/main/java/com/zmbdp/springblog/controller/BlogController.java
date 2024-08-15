package com.zmbdp.springblog.controller;

import com.zmbdp.springblog.model.BlogInfo;
import com.zmbdp.springblog.model.Result;
import com.zmbdp.springblog.service.BlogService;
import com.zmbdp.springblog.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/blog")
public class BlogController {
    @Autowired
    private BlogService blogService;

    /**
     * 获取博客列表
     * @return
     */
    @RequestMapping("/getlist")
    public List<BlogInfo> getList() {
        return blogService.getList();
    }

    /**
     * 根据博客 id 获取博客
     * @param blogId
     * @return
     */
    @RequestMapping("/getBlogDetail")
    public BlogInfo getBlogDetail(Integer blogId, HttpServletRequest request) {
        BlogInfo blogInfo = blogService.getBlogDetail(blogId);
        String token = request.getHeader("user_token");
        // 判断博客作者和登录用户是不是同一个
        Integer userId = JwtUtils.getUserIdToken(token);
        if (userId != null && Objects.equals(userId, blogInfo.getUserId())) {
            blogInfo.setLoginUser(true);
        } else {
            blogInfo.setLoginUser(false);
        }
        return blogInfo;
    }

    @RequestMapping("/update")
    public Boolean update(String title, String content, Integer id) {
        if (title == null || content == null || id == null || id < 1) {
            System.err.println("在这里就走了");
            return false;
        }
        return blogService.update(title, content, id);
    }
    /**
     * 删除博客
     * @param blogId
     * @return
     */
    @RequestMapping("/delete")
    public Boolean delete(Integer blogId) {
        if (blogId == null || blogId < 1) {
            return false;
        }
        return blogService.delete(blogId);
    }

    @RequestMapping("/insertBlog")
    public Result insertBlog(String title, String content, HttpServletRequest request) {
        BlogInfo blogInfo = new BlogInfo();
        blogInfo.setTitle(title);
        blogInfo.setContent(content);
        // 拿到 token 然后再解析出 userId
        Integer userId = JwtUtils.getUserIdToken(request.getHeader("user_token"));
        if (userId == null || userId < 1) {
            return Result.fail("用户未登录",false);
        }
        blogInfo.setUserId(userId);
        if (blogService.insertBlog(blogInfo) < 1) {
            return Result.fail("博客发布失败");
        }
        return Result.success("success");
    }
}
