package Mybatis;

import Mybatis.MyDAO.UserMapper;
import Mybatis.MyDTO.User;
import Mybatis.Utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;
import java.util.List;


public class testdemo {

    @Test
    public void test1() {
        //第一步：获得SqlSession对象
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        List<User> userList = userMapper.getUserList();
//        User user = userMapper.getUserById(1);
        for (User user : userList) {
            System.out.println(user);
        }
    }
}
