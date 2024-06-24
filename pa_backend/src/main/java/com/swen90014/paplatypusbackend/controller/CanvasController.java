package com.swen90014.paplatypusbackend.controller;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/canvas")
public class CanvasController {

    @Value("${canvas.domain}")
    private String canvasDomain;

    @Value("${canvas.access.token}")
    private String accessToken;

    @GetMapping("/courses")
    public ResponseEntity<String> getCourses() {
        RestTemplate restTemplate = new RestTemplate();
        String url = canvasDomain + "/courses";

        // Set the Authorization header with your Canvas access token
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);


        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        return ResponseEntity.ok(response.getBody());
    }
    @GetMapping("/users")
    public ResponseEntity<String> getUsersInOneCourse() {
        String url = canvasDomain + "/courses/142280000000096227/users";

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);

        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        return ResponseEntity.ok(response.getBody());
    }
    @GetMapping("/self")
    public ResponseEntity<String> getSelfData() {
        RestTemplate restTemplate = new RestTemplate();
        String url = canvasDomain + "/users/self";

        // Set the Authorization header with your Canvas access token
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);

        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        return ResponseEntity.ok(response.getBody());
    }
    @GetMapping("/students")
    public ResponseEntity<String> getStudentsInOneCourse() {
        String url = canvasDomain + "/courses/142280000000096227/users?enrollment_type[]=student";

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);

        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        return ResponseEntity.ok(response.getBody());
    }

    @GetMapping("/teachers")
    public ResponseEntity<String> getTeachersInOneCourse() {
        String url = canvasDomain + "/courses/142280000000096227/users?enrollment_type[]=teacher";

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);

        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        return ResponseEntity.ok(response.getBody());
    }
    @GetMapping("/assignments")
    public ResponseEntity<String> getAssignments() {
        String url = canvasDomain + "/courses/142280000000096227/assignments";

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);

        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        return ResponseEntity.ok(response.getBody());
    }
    @GetMapping("/scores")
    public ResponseEntity<String> getScoreForEachStudentAssignment() {
        String url = canvasDomain + "/courses/142280000000096227/assignments/45410/submissions/111274";

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);

        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        return ResponseEntity.ok(response.getBody());
    }

}

