package com.mwj.mwjwork.sample.system.repository;

import com.mwj.mwjwork.sample.system.entity.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void findByName() {
        User user = new User();
        user.setName("vitas");
        user.setAge(20);
        user = userRepository.save(user);

        assertEquals("vitas", user.getName());
        assertEquals(20, user.getAge().intValue());

        List<User> userList = userRepository.findByName("vitas");
        assertEquals(1, userList.size());

        userRepository.deleteAll();
        userList = userRepository.findAll();
        assertEquals(0, userList.size());
    }
}