package com.example.vue;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:5174")
public class MyController {
    
    @GetMapping(path = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String someJson() {
        return "{\"message\": \"test JSON\"}";
    }

    @GetMapping("/v/**")
	public String viteProject() {
        return "forward:/vite-project/index.html";
    }

}
