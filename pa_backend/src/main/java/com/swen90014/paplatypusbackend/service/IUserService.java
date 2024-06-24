package com.swen90014.paplatypusbackend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.swen90014.paplatypusbackend.domain.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface IUserService extends IService<User> {
    List<User> getUserByName(String name);
    User getUserByToken(String token);
    void insertOrUpdateBatch(List<User> users);
    void insertOrUpdate(User user);
}
