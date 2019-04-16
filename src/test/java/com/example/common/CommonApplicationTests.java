package com.example.common;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("201")
public class CommonApplicationTests {

    @Resource
    private DataSource dataSource;

    @Test
    public void contextLoads() {
        System.out.println("数据源>>>>>>" + dataSource.getClass());
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            System.out.println("连接>>>>>>>>>" + connection);
            System.out.println("连接地址>>>>>" + connection.getMetaData().getURL());
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
