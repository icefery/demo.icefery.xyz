package com.example;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import java.util.List;

@SpringBootTest
@TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
public class AppTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeAll
    public void beforeAll() {
        jdbcTemplate.execute(
            """
            drop table if exists t_user;
            create table t_user (
                id       bigint,
                username varchar(64),
                age      int,
                primary key(id)
            );
            insert into t_user values(1, 'icefery', 23);
            """
        );
    }

    @AfterAll
    public void afterAll() {
        jdbcTemplate.execute("drop table if exists t_user;");
    }

    @Test
    @Order(value = 1)
    public void test1() {
        List<User> list = jdbcTemplate.query("select * from t_user", new DataClassRowMapper<>(User.class));
        Assertions.assertFalse(list.isEmpty());
        System.out.println(list);
    }

    @Test
    @Order(value = 2)
    public void test2() {
        User user = jdbcTemplate.queryForObject("select * from t_user where id = ?", new DataClassRowMapper<>(User.class), 1);
        Assertions.assertNotNull(user);
        System.out.println(user);
    }

    public record User(Long id, String username, Integer age) {}
}
