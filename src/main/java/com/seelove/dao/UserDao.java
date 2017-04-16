package com.seelove.dao;

import com.seelove.entity.local.user.User;
import com.seelove.provider.SqlProvider;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Dao层
 *
 * @author L.jinzhu on 2017/3/30
 */
@Repository
public interface UserDao {
    @Select("select * from userinfo where userId=#{userId}")
    User findById(@Param("userId") Long userId);

    @SelectProvider(type = SqlProvider.class, method = "userFindAll")
    List<User> findAll(@Param("ageStart") int ageStart, @Param("ageEnd") int ageEnd, @Param("sex") String sex, @Param("cityCode") String cityCode);

    @Insert("insert into userinfo (userId,nickName,token4RongCloud,headUrl) values (#{userId},#{nickName},#{token4RongCloud},#{headUrl})")
    void create(@Param("userId") Long userId, @Param("nickName") String nickName, @Param("token4RongCloud") String token4RongCloud, @Param("headUrl") String headUrl);
}
