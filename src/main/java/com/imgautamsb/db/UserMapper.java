package com.imgautamsb.db;

import com.imgautamsb.security.entity.TenantUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {

    @Select("SELECT * FROM t_user WHERE username=#{username}")
    TenantUser findUserByUsername(String username);

    @Select("SELECT origin_address FROM origin_master WHERE flag=1")
    List<String> getOriginList();
}
