package com.mwjwork.sample.system.repository;

import com.mwjwork.sample.system.entity.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
        User user = new User(1L, "vitas", 20);
        userRepository.save(user);

        user = userRepository.findById(1L).get();
        assertEquals(1L, user.getId().longValue());
        assertEquals("vitas", user.getName());
        assertEquals(20, user.getAge().intValue());
    }
}