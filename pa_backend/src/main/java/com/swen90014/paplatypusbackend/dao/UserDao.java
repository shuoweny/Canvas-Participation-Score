package com.swen90014.paplatypusbackend.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.swen90014.paplatypusbackend.domain.Teacher;
import com.swen90014.paplatypusbackend.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserDao extends BaseMapper<User> {
    @Select("SELECT * FROM User WHERE name = #{name}")
    List<User> getUserByName(String name);

    @Select("SELECT * FROM User WHERE token = #{token}")
    User getUserByToken(String token);
    @Insert({
            "<script>",
            "INSERT INTO \"user\" (id, name, avatar_url, last_name, first_name, token) VALUES ",
            "<foreach item='item' collection='list' separator=','>",
            "(#{item.id}, #{item.name}, #{item.avatarUrl}, #{item.lastName}, #{item.firstName}, #{item.token})",
            "</foreach>",
            "ON CONFLICT (id) DO UPDATE SET",
            "name = EXCLUDED.name, avatar_url = EXCLUDED.avatar_url, last_name = EXCLUDED.last_name, first_name = EXCLUDED.first_name, token = EXCLUDED.token",
            "</script>"
    })
    void insertOrUpdateBatch(@Param("list") List<User> users);
    @Insert("INSERT INTO \"user\" (id, name, avatar_url, last_name, first_name, token) VALUES " +
            "(#{user.id}, #{user.name}, #{user.avatarUrl}, #{user.lastName}, #{user.firstName}, #{user.token}) " +
            "ON CONFLICT (id) DO UPDATE SET " +
            "name = EXCLUDED.name, avatar_url = EXCLUDED.avatar_url, last_name = EXCLUDED.last_name, first_name = EXCLUDED.first_name, token = EXCLUDED.token")
    void insertOrUpdate(@Param("user") User user);

}
