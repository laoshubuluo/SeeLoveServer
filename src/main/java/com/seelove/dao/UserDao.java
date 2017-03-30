package com.seelove.dao;

import com.seelove.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

/**
 * 实体类
 *
 * @author L.jinzhu on 2017/3/30
 */
@Service
public interface UserDao {
    @Select("select * from test where id=#{id}")
    User findById(@Param("id") Long id);
}
