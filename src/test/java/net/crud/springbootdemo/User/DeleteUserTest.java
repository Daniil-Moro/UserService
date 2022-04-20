package net.crud.springbootdemo.User;


import net.crud.springbootdemo.repository.UserRepository;
import net.crud.springbootdemo.model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DeleteUserTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestHelper testHelper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private User user;

    @Before
    public void setup() {
//        Role role = roleService.findByName(user.getRole());
//        user.setRole(role);
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        user = userRepository.saveAndFlush(new User("Bob", "password", "1"));
        user.setName("BobiS");
        user.setPassword(passwordEncoder.encode("password"));
    }

    @After
    public void afterAllTests() {
        userRepository.delete(user);
    }

    @Test
    public void deleteUserOne() {
        String resourceUrl = "/api/users/" + user.getName().toString();

        ResponseEntity<User> responseEntity = restTemplate.exchange(resourceUrl, HttpMethod.DELETE, testHelper.getRequestHeaders(), User.class);

        // Check for proper status
        assertEquals(204, responseEntity.getStatusCodeValue());
        //assertEquals(MediaType.APPLICATION_JSON_UTF8, responseEntity.getHeaders().getContentType());
    }

    @Test
    public void deleteNonExistantUser() {
        String resourceUrl = "/api/users/5555";

        ResponseEntity<User> responseEntity = restTemplate.exchange(resourceUrl, HttpMethod.DELETE, testHelper.getRequestHeaders(), User.class);

        // Check for proper status
        assertEquals(404, responseEntity.getStatusCodeValue());
        assertEquals(MediaType.APPLICATION_JSON_VALUE, responseEntity.getHeaders().getContentType());
    }
}
