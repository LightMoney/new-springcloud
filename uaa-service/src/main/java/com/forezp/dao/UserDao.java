package com.forezp.dao;


import com.forezp.entity.User;
import org.apache.ibatis.annotations.Mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by fangzhipeng on 2017/5/27.
 */
@Mapper
@Repository
public interface UserDao {

    User findByUsername(@Param("username") String username);
}
