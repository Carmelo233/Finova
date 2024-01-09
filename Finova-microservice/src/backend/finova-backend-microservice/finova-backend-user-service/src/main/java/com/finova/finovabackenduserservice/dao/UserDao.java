package com.finova.finovabackenduserservice.dao;

import com.finova.finovabackendmodel.domain.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;


@Mapper
@Repository
public interface UserDao {
    User queryByUsername(String username);

    User queryByPhoneNumber(String phoneNumber);

    Integer insertUser(User user);

    Integer insertUserWithPhoneNumber(User user);

    Integer updatePhoneNumber(String username, String phoneNumber);

    User queryByUsernameAndEcryptPassword(@Param("username") String username, @Param("encryptPassword") String encryptPassword);

    long selectCountByUsername(String username);
}
