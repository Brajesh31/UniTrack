package com.bash.Unitrack.Service;

import com.bash.Unitrack.Data.DTO.UserDTO;
import com.bash.Unitrack.Data.DTO.UserRequest;
import com.bash.Unitrack.Data.Models.Student;
import com.bash.Unitrack.Data.Models.User;
import com.bash.Unitrack.Exceptions.NotFoundException;
import com.bash.Unitrack.Repositories.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    String message = "User does not exist";

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Bad credentials"));
    }

    public List<UserDTO> fetchUsers() {
        return userRepository.findAll().stream().map(UserDTO::new).collect(Collectors.toList());
    }

    public ResponseEntity<String> update(UserRequest userRequest) throws NotFoundException {
        User user = userRepository.findByUsername(userRequest.username())
                .orElseThrow(() -> new NotFoundException(message));

        if (userRequest.firstName() != null){
            user.setFirstName(userRequest.firstName());
        }
        if (userRequest.lastName() != null){
            user.setLastName(userRequest.lastName());
        }
        if (userRequest.email() != null){
            user.setEmail(userRequest.email());
        }
        if (userRequest.password() != null){
            user.setPassword(passwordEncoder.encode(userRequest.password()));
        }

        if (user instanceof Student student){
            if (userRequest.program() != null){
                student.setProgram(userRequest.program());
            }
            if (userRequest.indexNumber() != null){
                student.setIndexNumber(userRequest.indexNumber());
            }
            userRepository.save(student);
            return ResponseEntity.ok("User details updated");
        }

        userRepository.save(user);
        return ResponseEntity.ok("User details updated");
    }

    public ResponseEntity<String> delete(Long id) throws NotFoundException {

        User user = userRepository.findById(id).
                orElseThrow(() -> new NotFoundException(message));
        userRepository.delete(user);
        return ResponseEntity.ok("User deleted");
    }

    public UserDTO fetchUser(Long id) throws NotFoundException {
        User user = userRepository.findById(id).
                orElseThrow(() -> new NotFoundException(message));
        return new UserDTO(user);
    }
}

