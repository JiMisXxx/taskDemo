package Mybatis.MyDAO;

import Mybatis.MyDTO.User;

import java.util.List;

public interface UserMapper {
    //查询全部用户
    List<User> getUserList();
    //根据ID查询用户
    User getUserById(int userid);
    //增加一个用户
    int addUser(User user);
    //修改用户
    int updateUser(User user);
    //删除一个用户
    void deleteUser(int userid);
}