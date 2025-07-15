package com.bash.Unitrack.Service;

import com.bash.Unitrack.Data.DTO.SignInRequest;
import com.bash.Unitrack.Data.DTO.UserRequest;
import com.bash.Unitrack.Data.Models.Lecturer;
import com.bash.Unitrack.Data.Models.Role;
import com.bash.Unitrack.Data.Models.Student;
import com.bash.Unitrack.Data.Models.User;
import com.bash.Unitrack.Exceptions.BadCredentialsException;
import com.bash.Unitrack.Repositories.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final TokenService tokenService;

    private final AuthenticationManager authenticationManager;

    public AuthenticationService(UserRepository userRepository,
                                 PasswordEncoder passwordEncoder,
                                 AuthenticationManager authenticationManager,
                                 TokenService tokenService){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    public String getUsername(){
        Object context = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Jwt jwt = (Jwt) context;
        return jwt.getSubject();
    }

    public Long getId(){
        Object context = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Jwt jwt = (Jwt) context;
        return jwt.getClaim("user_id");
    }

    public ResponseEntity<String> register(UserRequest userRequest) throws BadCredentialsException {
        if (userRequest.username() == null || userRequest.password() == null || userRequest.role() == null) {
            throw new BadCredentialsException("Enter credentials");
        }
        if (userRepository.findByUsername(userRequest.username()).isPresent()) {
            throw new BadCredentialsException("User already exists");
        }
        else {

            User newUser = new User();
            if (userRequest.role().equals("LECTURER")){
                newUser = new Lecturer();
                BeanUtils.copyProperties(userRequest, newUser);
                newUser.setPassword(passwordEncoder.encode(userRequest.password()));
                newUser.setRole(Role.LECTURER);
            }

            if (userRequest.role().equals("STUDENT")){
                newUser = new Student();
                BeanUtils.copyProperties(userRequest, newUser);
                newUser.setPassword(passwordEncoder.encode(userRequest.password()));
                newUser.setRole(Role.STUDENT);
            }

            if (userRequest.role().equals("ADMIN")){
                newUser = new User();
                BeanUtils.copyProperties(userRequest, newUser);
                newUser.setPassword(passwordEncoder.encode(userRequest.password()));
                newUser.setRole(Role.ADMIN);
            }

            userRepository.save(newUser);

            return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully");
        }

    }

    public ResponseEntity<SignInRequest> signIn(UserRequest userRequest) throws BadCredentialsException {

        if (userRequest.username() == null || userRequest.password() == null) {
            throw new BadCredentialsException("Enter username and password");
        }

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken
                        (userRequest.username(), userRequest.password()));
        User user = (User) authentication.getPrincipal();
        String role = user.getRole().toString();
        String jwt = tokenService.generate(user);
        return ResponseEntity.ok(new SignInRequest(jwt, role));
    }
}
