package com.example.vue.controllers;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
// Enables Cross-Origin Resource Sharing (CORS) from the specified origin.
@CrossOrigin(origins = "http://localhost:5173")
public class MyController {

    // Handles GET requests to "/api" and returns a JSON response.
    @GetMapping(path = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String someJson() {
        // Returns a simple JSON response with a message.
        return "{\"message\": \"test JSON\"}";
    }

    // Handles GET requests to paths that start with "/v/" and forwards them to a specific HTML file.
    @GetMapping("/v/**")
    public String viteProject() {
        // Forwards requests to "/v/**" to the Vite project HTML file located at "/vite-project/index.html".
        return "forward:/vite-project/index.html";
    }
}
