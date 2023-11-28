package com.finova.finovabackenduserservice.dao;

import com.finova.finovabackendmodel.domain.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;


@Mapper
@Repository
public interface UserDao {
    @Select("select * from user where username = #{username}")
    User queryByUsername(String username);

    @Select("select * from user where phoneNumber = #{phoneNumber}")
    User queryByPhoneNumber(String phoneNumber);

    @Insert("insert into user(username, password) values(#{username}, #{password})")
    @Options(useGeneratedKeys = true, keyProperty = "uid", keyColumn = "uid")
    Integer insertUser(User user);

    @Insert("insert into user(username, password, phoneNumber) values(#{username}, #{password}, #{phoneNumber})")
    @Options(useGeneratedKeys = true, keyProperty = "uid", keyColumn = "uid")
    Integer insertUserWithPhoneNumber(User user);

    @Update("update user set phoneNumber = #{phoneNumber} where username = #{username}")
    @Options(useGeneratedKeys = true, keyProperty = "uid", keyColumn = "uid")
    Integer updatePhoneNumber(String username, String phoneNumber);
}
