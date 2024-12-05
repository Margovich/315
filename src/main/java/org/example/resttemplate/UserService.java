package org.example.resttemplate;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

public class UserService {
    private static final String BASE_URL = "http://94.198.50.185:7081/api/users";
    private RestTemplate restTemplate;
    private HttpHeaders headers;
    private String sessionId;
    private StringBuilder finalCode;

    public UserService() {
        restTemplate = new RestTemplate();
        headers = new HttpHeaders();
        finalCode = new StringBuilder();
    }

    public void execute() {
        getAllUsers();
        addUser();
        updateUser();
        deleteUser();

        System.out.println(finalCode.toString());
    }

    private void getAllUsers() {
        ResponseEntity<User[]> response = restTemplate.exchange(BASE_URL, HttpMethod.GET, null, User[].class);
        sessionId = response.getHeaders().getFirst(HttpHeaders.SET_COOKIE);
    }

    private void addUser() {
        headers.set("Cookie", sessionId);
        User newUser = new User();
        newUser.setId(3L);
        newUser.setName("James");
        newUser.setLastName("Brown");
        newUser.setAge((byte) 27);

        HttpEntity<User> requestEntity = new HttpEntity<>(newUser, headers);
        ResponseEntity<String> response = restTemplate.exchange(BASE_URL, HttpMethod.POST, requestEntity, String.class);
        String responseBody = response.getBody();
        finalCode.append(responseBody.substring(0, 6));
    }


    private void updateUser() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Cookie", sessionId);
        User updatedUser = new User();
        updatedUser.setId(3L);
        updatedUser.setName("Thomas");
        updatedUser.setLastName("Shelby");
        updatedUser.setAge((byte) 27);

        HttpEntity<User> requestEntity = new HttpEntity<>(updatedUser, headers);
        ResponseEntity<String> response = restTemplate.exchange(BASE_URL, HttpMethod.PUT, requestEntity, String.class);
        String responseBody = response.getBody();
        finalCode.append(responseBody.substring(0, 6));
    }

    private void deleteUser() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Cookie", sessionId);
        ResponseEntity<String> response = restTemplate.exchange(BASE_URL + "/3", HttpMethod.DELETE, new HttpEntity<>(headers), String.class);
        String responseBody = response.getBody();
        finalCode.append(responseBody.substring(0, 6));
    }
}
