package net.crud.springbootdemo.User;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TestHelper {

    private final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private TestRestTemplate restTemplate;

    public HttpEntity getRequestHeaders() {
        List<MediaType> acceptTypes = new ArrayList<MediaType>();
        acceptTypes.add(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE));

        HttpHeaders reqHeaders = new HttpHeaders();
        reqHeaders.setContentType(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE));
        reqHeaders.setAccept(acceptTypes);

        return new HttpEntity<String>("parameters", reqHeaders);
    }

    public HttpEntity getPostRequestHeaders(String jsonPostBody) {
        List<MediaType> acceptTypes = new ArrayList<MediaType>();
        acceptTypes.add(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE));

        ObjectNode loginRequest = mapper.createObjectNode();
        loginRequest.put("username","Dani");
        loginRequest.put("password","password");
        JsonNode loginResponse = restTemplate.postForObject("/authenticate", loginRequest, JsonNode.class);

        HttpHeaders reqHeaders = new HttpHeaders();
        reqHeaders.add("Authorization", "Bearer " + loginResponse.get("token").textValue());
        reqHeaders.setContentType(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE));
        reqHeaders.setAccept(acceptTypes);

        return new HttpEntity<String>(jsonPostBody, reqHeaders);
    }

    public String userUrlHelper(String resourceUrl, String resourceId) {
        return resourceUrl + "/" + resourceId;
    }

    public JSONObject constructUser(String username, String password, String firstName, String lastName, String email, String role) {
        JSONObject contactBody = new JSONObject();

        try {
            if(null != username) {
                contactBody.put("name", username);
            }
            contactBody.put("password", password);
            contactBody.put("firstName", firstName);
            contactBody.put("lastName", lastName);
            contactBody.put("email", email);
            contactBody.put("role", role);

            return contactBody;
        } catch(JSONException e) {
            return null;
        }
    }

}
