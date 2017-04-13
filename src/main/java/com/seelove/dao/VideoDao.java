package com.seelove.dao;

import com.seelove.entity.local.video.Video;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * dao层
 *
 * @author L.jinzhu on 2017/3/30
 */
@Repository
public interface VideoDao {
    @Select("select * from videoinfo where userId=#{userId}")
    List<Video> findByUser(@Param("userId") Long userId);

    @Insert("insert into videoinfo (videoId,userId,videoTitle,videoTime,isDefault,videoImg,videoUrl,videoPlayTime,remark) values (#{videoId},#{userId},#{videoTitle},#{videoTime},#{isDefault},#{videoImg},#{videoUrl},#{videoPlayTime},#{remark})")
    void create(@Param("videoId") Long videoId, @Param("userId") Long userId, @Param("videoTitle") String videoTitle, @Param("videoTime") String videoTime, @Param("isDefault") String isDefault, @Param("videoImg") String videoImg, @Param("videoUrl") String videoUrl, @Param("videoPlayTime") String videoPlayTime, @Param("remark") String remark);
}
