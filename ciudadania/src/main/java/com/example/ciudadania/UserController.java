package com.example.ciudadania;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import reactor.core.publisher.Mono;

import java.util.Base64;
import java.util.Map;

@Controller
public class UserController {
    
    // @Autowired
    // private OAuth2RestTemplate restTemplate;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/user")
    public String user(Model model,
            @AuthenticationPrincipal OidcUser oidcUser) {
        model.addAttribute("userName", oidcUser.getName());
        model.addAttribute("audience", oidcUser.getAudience());
        return "user";
    }

    @GetMapping("/cd-callback/login")
    @ResponseBody
    public String callback(@RequestParam(name = "code") String code,
            @RequestParam(name = "state") String state,
            @RequestParam(name = "session_state") String sessionState) {
                
        return "Code: " + code + "<br/>State: " + state + "<br/>SessionState: " + sessionState;
    }

    @GetMapping("/user-info")
    public Mono<String> getUserInfo(@AuthenticationPrincipal Mono<OAuth2User> principal) {
        // Access user information
        String name = principal.block().getName();
        Map<String, Object> attributes = principal.block().getAttributes();

        return Mono.just("Logged-in user: " + name + ", attributes: " + attributes);
    }
}
