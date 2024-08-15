package com.zmbdp.springblog.mapper;

import com.zmbdp.springblog.model.BlogInfo;
import com.zmbdp.springblog.model.UserInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface BlogInfoMapper {
    // 添加博客
    @Insert("insert into blog_info (title, content, user_id) values (#{title}, #{content}, #{userId})")
    Integer insertBlog(BlogInfo blogInfo);

    // 获取博客列表
    @Select("select id, title, content, user_id, delete_flag, create_time, update_time " +
            "from blog_info where delete_flag = 0 order by update_time desc")
    List<BlogInfo> selectAllBlog();

    // 根据博客 id 获取博客信息
    @Select("select id, title, content, user_id, delete_flag, create_time, update_time " +
            "from blog_info where id = #{id} and delete_flag = 0")
    BlogInfo selectBlogByBlogId(Integer id);

    // 编辑博客
    @Update("update blog_info set title = #{title}, content = #{content}" +
            "where id = #{id} and delete_flag = 0")
    Integer updateBlog(BlogInfo blogInfo);

    // 根据博客 id，删除博客
    @Update("update blog_info set delete_flag = 1 where id = #{id}")
    Integer deleteBlog(Integer id);
}
