package com.zmbdp.springblog.mapper;

import com.zmbdp.springblog.model.UserInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserInfoMapper {
    // 注册，返回收到影响的行数
    @Insert("insert into user_info (user_name, password, github_url) values (#{userName}, #{password}, #{githubUrl})")
    Integer insertUser(@Param("userName") String userName, @Param("password") String password, @Param("githubUrl") String githubUrl);

    // 登录，返回 password 用来校验
    @Select("select password from user_info where user_name = #{userName} and delete_flag = 0")
    String selectPasswordByName(String userName);

    // 根据用户名查询用户信息
    @Select("select id, user_name, password, github_url, delete_flag, create_time, update_time " +
            "from user_info where user_name = #{userName} and delete_flag = 0")
    UserInfo selectUserByName(String userName);

    // 根据用户 id，查询用户信息
    @Select("select id, user_name, password, github_url, delete_flag, create_time, update_time " +
            "from user_info where id = #{id} and delete_flag = 0")
    UserInfo selectUserByUserId(Integer id);
}
