/**
 * Copyright 2017 Shi Jin Licensed under the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.shijin.learn.movingdemo.users.mapper;

import java.util.Collection;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.jdbc.SQL;

import com.shijin.learn.movingdemo.users.api.LoginUser;
import com.shijin.learn.movingdemo.users.api.Pagination;

/**
 * @author shijin
 *
 */
@Mapper
public interface UserMapper {

  @Select("select * from companyusers where id = #{id}")
  LoginUser getUser(@Param("id") int id);

  @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
  @Insert("insert into companyusers (id, company, username, password) values (null, #{company}, #{username}, #{password}) ")
  int addUser(LoginUser user);

  @Update("update companyusers set company=#{company}, username=#{username}, password=#{password}, enabled=#{enabled} where id = #{id}")
  int updateUser(LoginUser user);

  @Delete("delete from companyusers where id = #{id}")
  int deleteUser(int id);

  @Select("select * from companyusers where company = #{company}")
  Collection<LoginUser> getUsers(String company);

  /**
   * retrive all user data according to query parameters, like name, company, pagination, etc.
   * select * from companyusers where company = 'Learn1' and username like '%in%' limit 2, 10;
   */
  @SelectProvider(type = UserSqlBuilder.class, method = "buildGetUsersList")
  Collection<LoginUser> getUsersList(@Param("param") LoginUser param,
      @Param("page") Pagination page);

  @SelectProvider(type = UserSqlBuilder.class, method = "buildGetUsersListCount")
  long getUsersListCount(@Param("param") LoginUser param, @Param("page") Pagination page);
  
  class UserSqlBuilder {
    
    public String buildGetUsersListCount(Map<?, ?> parameters) {
      return generateGetUsersList(parameters, true);
    }
    
    public String buildGetUsersList(Map<?, ?> parameters) {
      return generateGetUsersList(parameters, false);
    }
    
    private String generateGetUsersList(Map<?, ?> parameters, boolean isCount) {
      LoginUser userParam = (LoginUser) parameters.get("param");
      Pagination page = (Pagination) parameters.get("page");
      String sql = new SQL() {
        {
          if (isCount) {
            SELECT("count(*)"); 
          } else {
            SELECT("*");
          }
          FROM("companyusers");
          if (userParam != null) {
            if (userParam.getCompany() != null) {
              WHERE("company = '" + userParam.getCompany() + "'");
            }
            if (userParam.getUsername() != null) {
              WHERE("username like '%" + userParam.getUsername() + "%'");
            }
            if (userParam.getEnabled() != null) {
              WHERE("enabled is " + userParam.getEnabled());
            }
          }
        }
      }.toString();

      if (page != null && !isCount) {
        sql += page.getMySql();
      }

      return sql;
    }
  }
}
