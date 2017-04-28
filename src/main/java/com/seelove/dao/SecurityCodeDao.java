package com.seelove.dao;

import com.seelove.entity.local.system.SecurityCode;
import com.seelove.entity.local.user.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Dao层
 *
 * @author L.jinzhu on 2017/3/30
 */
@Repository
public interface SecurityCodeDao {
    @Insert("insert into securitycodeinfo (phoneNumber,code,type,sendTime) values (#{phoneNumber},#{code},#{type},#{sendTime})")
    void create(@Param("phoneNumber") String phoneNumber, @Param("code") String code, @Param("type") String type, @Param("sendTime") String sendTime);

    @Delete("delete from securitycodeinfo where phoneNumber=#{phoneNumber} and type=#{type}")
    void delete(@Param("phoneNumber") String phoneNumber, @Param("type") String type);

    @Select("select * from securitycodeinfo where phoneNumber=#{phoneNumber} and type=#{type} and code=#{code} limit 0,1")
    SecurityCode find(@Param("phoneNumber") String phoneNumber, @Param("type") String type, @Param("code") String code);
}
