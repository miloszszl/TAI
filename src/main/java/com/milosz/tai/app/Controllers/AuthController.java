package com.milosz.tai.app.Controllers;

import com.milosz.tai.app.Entities.Parameters.RoleName;
import com.milosz.tai.app.Entities.Role;
import com.milosz.tai.app.Entities.User;
import com.milosz.tai.app.Exceptions.AppException;
import com.milosz.tai.app.Payload.*;
import com.milosz.tai.app.Repositories.RoleRepository;
import com.milosz.tai.app.Repositories.UserRepository;
import com.milosz.tai.app.Security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtTokenProvider tokenProvider;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, JwtTokenProvider tokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
    }

    @Bean(name = "multipartResolver")
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(100000);
        return multipartResolver;
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }

    @GetMapping("/checkNicknameAvailability")
    public UserNicknameAvailability checkNicknameAvailability(@RequestParam(name = "nickname") String nickname) {
        Boolean exists = !userRepository.existsByNickname(nickname);
        return new UserNicknameAvailability(exists);
    }

    @GetMapping("/checkEmailAvailability")
    public UserEmailAvailability checkEmailAvailability(@RequestParam(name = "email") String email) {
        Boolean exists = !userRepository.existsByEmail(email);
        return new UserEmailAvailability(exists);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid SignUpRequest signUpRequest) {

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity<>(new ApiResponse(false, "email", "Ten adres e-mail jest już zajęty"),
                    HttpStatus.BAD_REQUEST);
        }

        if (userRepository.existsByNickname(signUpRequest.getNickname())) {
            return new ResponseEntity<>(new ApiResponse(false, "nickname", "Ten nickname jest już zajęty"),
                    HttpStatus.BAD_REQUEST);
        }

        byte[] imageBytes;
        try {
            if (signUpRequest.getImage() == null || Arrays.equals(signUpRequest.getImage().getBytes(),"null".getBytes())) {
                File fi = new File("a.png");
                imageBytes = Files.readAllBytes(fi.toPath());
            } else {
                imageBytes = signUpRequest.getImage().getBytes();
            }
        } catch (IOException e) {
            return new ResponseEntity<>(new ApiResponse(false, "image", "Błąd przesyłania obrazu!"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

        String encodedPassword = passwordEncoder.encode(signUpRequest.getPassword());

        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new AppException("Rola nie jest dostępna!"));
        Set<Role> userRoles = Collections.singleton(userRole);

        // Creating user account
        User user = new User(signUpRequest.getNickname(), signUpRequest.getEmail(), encodedPassword, userRoles, imageBytes);

        User result = userRepository.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/users/{nickname}")
                .buildAndExpand(result.getNickname()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "",
                ""));
    }
}