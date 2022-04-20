package net.crud.springbootdemo.User;

import net.crud.springbootdemo.repository.UserRepository;
import net.crud.springbootdemo.model.User;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static junit.framework.TestCase.assertEquals;
import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CreateUserTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestHelper testHelper;

    private User createdUser;

    @After
    public void cleanup() {
        if(null != createdUser) {
            userRepository.delete(createdUser);
        }
    }

    @Test
    public void createNewUser() {
        String resourceUrl = "/api/users";
        String username = "BobiS";
        String password = "password";
        String firstName = "Bob";
        String lastName = "Smith";
        String email = "1234@sdfwe.cy";
        String role = "ROLE_USER";;

        JSONObject postBody = testHelper.constructUser(username, password, firstName, lastName, email, role);

        ResponseEntity<User> responseEntity =
                restTemplate.exchange(resourceUrl, HttpMethod.POST, testHelper.getPostRequestHeaders(postBody.toString()), User.class);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON_VALUE, responseEntity.getHeaders().getContentType());

        createdUser = responseEntity.getBody();
        assertEquals(firstName, createdUser.getFirstName());
        assertEquals(lastName, createdUser.getLastName());
//        assertEquals(phone, createdUser.getPhone());

        // Check Location HeaderURL
        String expectedLocationUrl = testHelper.userUrlHelper(resourceUrl, createdUser.getName().toString());
        String returnedLocationUrl = responseEntity.getHeaders().getLocation().toString();

        assertThat(returnedLocationUrl, containsString(expectedLocationUrl));
    }

    @Test
    public void createNewUserWithoutUsername() {
        String resourceUrl = "/api/users";
        String password = "password";
        String firstName = "Bob";
        String lastName = "Smith";
        String email = "1234@sdfwe.cy";
        String role = "ROLE_USER";;

        JSONObject postBody = testHelper.constructUser(null, password,firstName, lastName, email, role);

        ResponseEntity<User> responseEntity =
                restTemplate.exchange(resourceUrl, HttpMethod.POST, testHelper.getPostRequestHeaders(postBody.toString()), User.class);

        assertEquals(422, responseEntity.getStatusCodeValue());
        assertEquals(MediaType.APPLICATION_JSON_VALUE, responseEntity.getHeaders().getContentType());
    }

    @Test
    public void createNewContactWithBlankFirstName() {
        String resourceUrl = "/api/users";
        String username = "";
        String password = "password";
        String firstName = "Bob";
        String lastName = "Smith";
        String email = "1234@sdfwe.cy";
        String role = "ROLE_USER";

        JSONObject postBody = testHelper.constructUser(username, password, firstName, lastName, email, role);

        ResponseEntity<User> responseEntity =
                restTemplate.exchange(resourceUrl, HttpMethod.POST, testHelper.getPostRequestHeaders(postBody.toString()), User.class);

        assertEquals(422, responseEntity.getStatusCodeValue());
        assertEquals(MediaType.APPLICATION_JSON_VALUE, responseEntity.getHeaders().getContentType());
    }
}
