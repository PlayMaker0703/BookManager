package com.zyf.mapper;

import com.zyf.entity.AuthUser;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {
    @Select("select * from users where name=#{username}")
    AuthUser getPasswordByUsername(String username);

    @Insert("insert into users(name,role,password) values(#{name},#{role},#{password})")
    @Options(useGeneratedKeys = true,keyColumn = "id",keyProperty = "id")//主键自动生成的
    int registerUser(AuthUser user);

    @Insert("insert into student(uid,name,sex,grade) values(#{uid},#{name},#{sex},#{grade})")
    int addStudentInfo(@Param("uid") int uid, @Param("name") String name, @Param("sex") String sex, @Param("grade") String grade);

    @Select("select sid from student where uid=#{uid}")
    Integer getSidByUserId(int uid);

    @Select("select count(*) from student")
    int getStudentCount();

}
