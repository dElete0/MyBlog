package com.example.demo.dao;

import com.example.demo.model.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserDao {
    String table_name = " user ";
    String insert_fields = " name, password, salt, head_url, role ";
    String select_fields = " id, " + insert_fields;

    @Insert({"insert into",table_name,"(",insert_fields,") values (#{name},#{passwork},#{salt},#{headUrl},#{role})"})
    public void insertUser(User user);

    @Select({"select",select_fields,"from",table_name,"where id=#{id}"})
    public User selectById(int id);

    @Select({"select",select_fields,"from",table_name,"where name=#{name}"})
    public User selectByName(@Param("name") String name);

    @Delete({"delete from",table_name,"where id=#{id}"})
    public void deleteById(int id);
}
