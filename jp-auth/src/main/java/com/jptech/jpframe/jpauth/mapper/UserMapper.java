package com.jptech.jpframe.jpauth.mapper;

import com.jptech.jpframe.jpauth.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper {

    public SysUser getUserByLoginId(@Param("username")String username);
}
