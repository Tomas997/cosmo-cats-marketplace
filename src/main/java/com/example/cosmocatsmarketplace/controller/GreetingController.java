package com.example.cosmocatsmarketplace.controller;


import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/greetings")
public class GreetingController {

    @GetMapping
    public Map<String, String> githubLogin(@AuthenticationPrincipal OAuth2User principal) {
        String username = principal.getAttribute("login");
        String name = principal.getAttribute("name");
        String avatarUrl = principal.getAttribute("avatar_url");
        String profileUrl = principal.getAttribute("html_url");

        return Map.of(
                "message", "Welcome to the application!",
                "username", username,
                "name", name != null ? name : "N/A",
                "avatarUrl", avatarUrl,
                "profileUrl", profileUrl
        );
    }
}
