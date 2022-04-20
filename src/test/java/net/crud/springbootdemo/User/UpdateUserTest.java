package net.crud.springbootdemo.User;

import net.crud.springbootdemo.repository.UserRepository;
import net.crud.springbootdemo.model.User;
import net.crud.springbootdemo.service.RoleService;
import org.json.JSONObject;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
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
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;


import static junit.framework.TestCase.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UpdateUserTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private RestTemplate patchRestTemplate;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestHelper testHelper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleService roleService;

    private User userFirst;
    private User userSecond;

    @Before
    public void setup() {
        User user = new User("BobiS", passwordEncoder.encode("password"), roleService.findByName("ROLE_USER"));
        user.setFirstName("Bob");
        user.setLastName("Smith");
        user.setEmail("1234@sdfwe.cy");
        userFirst = userRepository.saveAndFlush(user);

        user = new User("MartyS", passwordEncoder.encode("password"), roleService.findByName("ROLE_USER"));
        user.setFirstName("Marty");
        user.setLastName("McFly");
        user.setEmail("12345@sdfwe.cy");
        userSecond = userRepository.saveAndFlush(user);


        this.patchRestTemplate = restTemplate.getRestTemplate();
        HttpClient httpClient = HttpClientBuilder.create().build();
        this.patchRestTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(httpClient));
    }

    @After
    public void afterAllTests() {
        userRepository.delete(userFirst);
        userRepository.delete(userSecond);
    }

    @Test
    public void updateUserOneWithPatch() throws Throwable {
        String resourceUrl = "/api/users/" + userFirst.getName().toString();

        JSONObject updateBody = new JSONObject();
        updateBody.put("firstName", "Robert");

        ResponseEntity<User> responseEntity =
                patchRestTemplate.exchange(resourceUrl, HttpMethod.PATCH, testHelper.getPostRequestHeaders(updateBody.toString()), User.class);

        // Check for proper status
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON_VALUE, responseEntity.getHeaders().getContentType());

        // Check for proper body
        User updatedUser = responseEntity.getBody();
        assertEquals("Robert", updatedUser.getFirstName());
        assertEquals("Smith", updatedUser.getLastName());
    }

    @Test
    public void updateUserOneWithPatchAndInvalidFirstName() throws Throwable {
        String resourceUrl = "/api/users/" + userFirst.getName().toString();

        JSONObject updateBody = new JSONObject();
        updateBody.put("firstName", "");

        ResponseEntity<User> responseEntity =
                patchRestTemplate.exchange(resourceUrl, HttpMethod.PATCH, testHelper.getPostRequestHeaders(updateBody.toString()), User.class);

        // Check for proper status
        assertEquals(422, responseEntity.getStatusCodeValue());
        assertEquals(MediaType.APPLICATION_JSON_VALUE, responseEntity.getHeaders().getContentType());
    }

    @Test
    public void updateUserOneEmailWithPatch() throws Throwable {
        String resourceUrl = "/api/users/" + userFirst.getName().toString();

        JSONObject updateBody = new JSONObject();
        updateBody.put("email", "123456@hd.com");

        ResponseEntity<User> responseEntity =
                patchRestTemplate.exchange(resourceUrl, HttpMethod.PATCH, testHelper.getPostRequestHeaders(updateBody.toString()), User.class);

        // Check for proper status
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON_UTF8, responseEntity.getHeaders().getContentType());

        // Check for proper body
        User updatedUser = responseEntity.getBody();
        assertEquals("Smith", updatedUser.getLastName());
        assertEquals("123456@hd.com", updatedUser.getEmail());
    }


    @Test
    public void updateUserTwoWithPut() throws Throwable {
        String resourceUrl = "/api/users/" + userSecond.getName().toString();

        JSONObject updateBody = new JSONObject();
        updateBody.put("firstName", "Martin");
        updateBody.put("lastName", "McFly");

        ResponseEntity<User> responseEntity =
                patchRestTemplate.exchange(resourceUrl, HttpMethod.PUT, testHelper.getPostRequestHeaders(updateBody.toString()), User.class);

        // Check for proper status
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON_VALUE, responseEntity.getHeaders().getContentType());

        // Check for proper body
        User updatedUser = responseEntity.getBody();
        assertEquals("Martin", updatedUser.getFirstName());
        assertEquals("McFly", updatedUser.getLastName());
        assertEquals(null, updatedUser.getEmail());
    }

    @Test
    public void updateUserTwoWithPutAndInvalidFirstName() throws Throwable {
        String resourceUrl = "/api/users/" + userSecond.getName().toString();

        JSONObject updateBody = new JSONObject();
        updateBody.put("firstName", "");
        updateBody.put("lastName", "McFly");

        ResponseEntity<User> responseEntity =
                restTemplate.exchange(resourceUrl, HttpMethod.PUT, testHelper.getPostRequestHeaders(updateBody.toString()), User.class);

        // Check for proper status
        assertEquals(422, responseEntity.getStatusCodeValue());
        assertEquals(MediaType.APPLICATION_JSON_VALUE, responseEntity.getHeaders().getContentType());
    }

    @Test
    public void patchUpdateNonExistantUser() throws Throwable {
        String resourceUrl = "/api/users/5555";

        JSONObject updateBody = new JSONObject();
        updateBody.put("firstName", "Jack");

        ResponseEntity<User> responseEntity =
                patchRestTemplate.exchange(resourceUrl, HttpMethod.PATCH, testHelper.getPostRequestHeaders(updateBody.toString()), User.class);

        // Check for proper status
        assertEquals(404, responseEntity.getStatusCodeValue());
        assertEquals(MediaType.APPLICATION_JSON_VALUE, responseEntity.getHeaders().getContentType());
    }

    @Test
    public void putUpdateNonExistantUser() throws Throwable {
        String resourceUrl = "/api/users/7777";

        JSONObject updateBody = new JSONObject();
        updateBody.put("firstName", "John");

        ResponseEntity<User> responseEntity =
                patchRestTemplate.exchange(resourceUrl, HttpMethod.PUT, testHelper.getPostRequestHeaders(updateBody.toString()), User.class);

        // Check for proper status
        assertEquals(404, responseEntity.getStatusCodeValue());
        assertEquals(MediaType.APPLICATION_JSON_VALUE, responseEntity.getHeaders().getContentType());
    }
}
