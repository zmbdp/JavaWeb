package com.example.bookmanagement.mapper;

import com.example.bookmanagement.model.UserInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    // 登录的时候查询账号密码的
    @Select("select password from user_info where user_name = #{userName}")
    String selectUser(String userName);

    // 注册的时候
    @Insert("insert into user_info (user_name, password) values (#{user_name}, #{password})")
    Integer insertUser(@Param("user_name") String userName,@Param("password") String password);

    @Select("select * from user_info where user_name = #{userName}")
    UserInfo selectUserByName(String userName);
}
