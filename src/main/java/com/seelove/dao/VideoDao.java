package com.seelove.dao;

import com.seelove.entity.local.user.User;
import com.seelove.entity.local.video.Video;
import com.seelove.provider.SqlProvider;
import org.apache.ibatis.annotations.*;
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

    @Select("select * from videoinfo order by videoId desc limit 0,5")
    List<Video> findCount5();

    @Select("select v.* from videoinfo v, user_video uv where uv.userId=#{userId} and uv.isDefault = 1 and uv.videoId = v.videoId")
    Video findDefault(@Param("userId") Long userId);

    @SelectProvider(type = SqlProvider.class, method = "videoFindAllByUserList")
    List<Video> findAllByUserList(@Param("userList") List<User> userList);

    @Insert("insert into videoinfo (" +
            "videoTitle,videoTime,videoImg,videoUrl,videoPlayTime,userId,remark" +
            ") values (" +
            "#{video.videoTitle},#{video.videoTime},#{video.videoImg},#{video.videoUrl},#{video.videoPlayTime},#{video.userId},#{video.remark}" +
            ")")
    @Options(useGeneratedKeys = true, keyProperty = "video.videoId")
    void create(@Param("video") Video video);

    @Insert("insert into user_video (videoId,userId,isDefault) values (#{videoId},#{userId},#{isDefault})")
    void createUserVideo(@Param("videoId") Long videoId, @Param("userId") Long userId, @Param("isDefault") String isDefault);
}
