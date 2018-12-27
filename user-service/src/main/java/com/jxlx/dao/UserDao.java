package com.jxlx.dao;

import com.jxlx.domain.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao {
  
  User selectById(Long id);

  List<User> selectUserList(User user);

  int update(User user);
  
  int insert(User account);
  
  User selectByEmail(String email);

}
