/**
 * Copyright 2017 Shi Jin
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.shijin.learn.movingdemo.users.mapper;

import java.util.Collection;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.shijin.learn.movingdemo.users.api.LoginUser;

/**
 * @author shijin
 *
 */
@Mapper
public interface UserMapper {

  @Select("select * from companyusers where id = #{id}")
  LoginUser getUser(@Param("id") int id);
  
  @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
  @Insert("insert into companyusers (id, company, username, password) values (null, #{company}, #{username}, #{password}) ")
  int addUser(LoginUser user);
  
  @Update("update companyusers set company=#{company}, username=#{username}, password=#{password}, enabled=#{enabled} where id = #{id}")
  int updateUser(LoginUser user);
  
  @Delete("delete from companyusers where id = #{id}")
  int deleteUser(int id);
  
  @Select("select * from companyusers where company = #{company}")
  Collection<LoginUser> getUsers(String company);
}
