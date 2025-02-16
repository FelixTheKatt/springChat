package io.github.felix.chatspringtest.endpoint;

import io.github.felix.chatspringtest.dto.LoginRequest;
import io.github.felix.chatspringtest.security.NimbusJwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final NimbusJwtUtil jwtUtil;

    public AuthController(AuthenticationManager authenticationManager, NimbusJwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request) throws Exception {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getPseudo(), request.getPassword())
        );
        User user = (User) authentication.getPrincipal();
        return jwtUtil.generateToken(user.getUsername());
    }


    @GetMapping("/me")
    public String getCurrentUser(Principal principal) {
        return "Bonjour, " + principal.getName();
    }
}
