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
    @Select("select v.* from videoinfo v, user_video uv where uv.userId=#{userId} and uv.videoId = v.videoId")
    List<Video> findByUser(@Param("userId") Long userId);

    @Select("select v.* from videoinfo v, user_video uv where uv.userId=#{userId} and uv.isDefault = 1 and uv.videoId = v.videoId")
    Video findDefault(@Param("userId") Long userId);

    @Insert("insert into videoinfo (videoId,videoTitle,videoTime,videoImg,videoUrl,videoPlayTime,remark) values (#{videoId},#{videoTitle},#{videoTime},#{videoImg},#{videoUrl},#{videoPlayTime},#{remark})")
    void create(@Param("videoId") Long videoId, @Param("videoTitle") String videoTitle, @Param("videoTime") String videoTime, @Param("videoImg") String videoImg, @Param("videoUrl") String videoUrl, @Param("videoPlayTime") String videoPlayTime, @Param("remark") String remark);

    @Insert("insert into user_video (videoId,userId,isDefault) values (#{videoId},#{userId},#{isDefault})")
    void createUserVideo(@Param("videoId") Long videoId, @Param("userId") Long userId, @Param("isDefault") String isDefault);
}
