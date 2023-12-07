package com.sky.mapper;

import com.sky.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
//    @Options(useGeneratedKeys = true,keyProperty = "id")
    @Select("select * from user where openid = #{openid}")
    User getUserById(String openid);
    @Options(useGeneratedKeys = true,keyProperty = "id")
    @Insert("insert into user(openid,create_time) values (#{openid},#{createTime})")
    Long insert(User build);
}
