package net.crud.springbootdemo.User;

import net.crud.springbootdemo.repository.UserRepository;
import net.crud.springbootdemo.model.User;
import net.crud.springbootdemo.service.RoleService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import static junit.framework.TestCase.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ListUserTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestHelper testHelper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleService roleService;

    private User singleUser;

    @Before
    public void addSingleUser() {
        User user = new User("BobiS", passwordEncoder.encode("password"), roleService.findByName("ROLE_USER"));
//        user.setName("BobiS");
//        user.setPassword(passwordEncoder.encode("password"));
        user.setFirstName("Bob");
        user.setLastName("Smith");
        user.setEmail("1234@sdfwe.cy");

        singleUser = userRepository.saveAndFlush(user);
    }

    @After
    public void afterAllTests() {
        userRepository.delete(singleUser);
    }

    @Test
    public void listUsers() {
        ResponseEntity<User[]> responseEntity = restTemplate.exchange("/api/users", HttpMethod.GET, testHelper.getRequestHeaders(), User[].class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON_VALUE, responseEntity.getHeaders().getContentType());
    }

    @Test
    public void getSingleUser() {
        String resourceUrl = "/api/users/" + singleUser.getName();
        ResponseEntity<User> responseEntity = restTemplate.exchange(resourceUrl, HttpMethod.GET, testHelper.getRequestHeaders(), User.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON_VALUE, responseEntity.getHeaders().getContentType());

        User parsedContact = responseEntity.getBody();
        assertEquals(singleUser.getName(), parsedContact.getName());
        assertEquals(singleUser.getFirstName(), parsedContact.getFirstName());
        assertEquals(singleUser.getLastName(), parsedContact.getLastName());
    }

    @Test
    public void handleNotFound() {
        String resourceUrl = "/api/users/5555";
        ResponseEntity<User> responseEntity = restTemplate.exchange(resourceUrl, HttpMethod.GET, testHelper.getRequestHeaders(), User.class);

        assertEquals(404, responseEntity.getStatusCodeValue());
        assertEquals(MediaType.APPLICATION_JSON_VALUE, responseEntity.getHeaders().getContentType());
    }

}
