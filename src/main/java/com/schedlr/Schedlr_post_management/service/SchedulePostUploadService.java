package com.schedlr.Schedlr_post_management.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.schedlr.Schedlr_post_management.Entity.SchedulePostUpload;
import com.schedlr.Schedlr_post_management.repo.SchedulePostUploadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class SchedulePostUploadService {

    @Autowired
    RestTemplate restTemplate;

    @Value("${schedlr.${spring.profiles.active}.url}")
    private String schedlrUrl;

    @Autowired
    SchedulePostUploadRepository schedulePostUploadRepository;

    @Scheduled(fixedDelay = 60000)
    public void callPlatformsOnTime() {
        List<SchedulePostUpload> allPosts = schedulePostUploadRepository.findAll();
        LocalDateTime now = LocalDateTime.now();

        allPosts.stream()
                .filter(post -> post.getScheduleTime().isBefore(now) || post.getScheduleTime().isEqual(now))
                .forEach(post -> {
                    System.out.println("PostId : " + post.getPostId() + " : PostTime : " + post.getScheduleTime() + " My time is : " + now);
                    String url = schedlrUrl + "postupload";

                    System.out.println("Url is : " + url);

                    // Prepare the headers
                    HttpHeaders headers = new HttpHeaders();
                    headers.setContentType(MediaType.MULTIPART_FORM_DATA);

                    // Prepare the platforms JSON
                    ObjectMapper objectMapper = new ObjectMapper();
                    String platformsJson = null;
                    try {
                        platformsJson = objectMapper.writeValueAsString(getSelectedPlatforms(post));
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }

                    // Prepare the form data
                    MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
                    body.add("userId", post.getUser().getUserid());
                    body.add("description", post.getDescription());
                    body.add("uploadImage", new ByteArrayResource(post.getImage()) {
                        @Override
                        public String getFilename() {
                            return "post_image.jpg";
                        }
                    });
                    body.add("platforms", platformsJson);

                    // Create an HTTP entity
                    HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

                    // Make the POST request
                    ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);

                    // Log the response
                    System.out.println("Response from API: " + response.getBody());

                    // If the response is successful, delete the post from the database
                    if (response.getStatusCode().is2xxSuccessful()) {
                        // Deleting the post from the DB
                        schedulePostUploadRepository.delete(post);
                        System.out.println("PostId " + post.getPostId() + " deleted from the database.");
                    } else {
                        // Optionally, log or handle the failure case
                        System.out.println("Failed to upload postId " + post.getPostId() + ". Not deleting from DB.");
                    }
                });
    }

    // Modify this method to return a List of platform names
    private List<String> getSelectedPlatforms(SchedulePostUpload post) {
        List<String> platforms = new ArrayList<>();
        if (post.isFb()) platforms.add("Facebook");
        if (post.isPinterest()) platforms.add("Pinterest");
        if (post.isTwitter()) platforms.add("Twitter");
        if (post.isLinkedin()) platforms.add("LinkedIn");
        return platforms;
    }
}
