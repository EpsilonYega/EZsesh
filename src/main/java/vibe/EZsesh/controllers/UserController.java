package vibe.EZsesh.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vibe.EZsesh.EntryPoint;
import vibe.EZsesh.configurations.JwtCore;
import vibe.EZsesh.dto.SigninRequest;
import vibe.EZsesh.entities.AppUser;
import vibe.EZsesh.entities.UserDetailsImpl;
import vibe.EZsesh.repositories.UserRepository;
import vibe.EZsesh.services.StorageService;

import java.io.IOException;
import java.util.Optional;

@RestController
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtCore jwtCore;
    @Autowired
    private StorageService storageService;

    @PostMapping("/register/user")
    public ResponseEntity<?> register(@RequestBody AppUser user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Optional<AppUser> userExists = userRepository.findByUsername(user.getUsername());

        if (userExists.isEmpty()) return ResponseEntity.ok(userRepository.save(user));

        return ResponseEntity.ok("Такой пользователь уже существует");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody SigninRequest signinRequest) {

        if (userRepository.findByUsername(signinRequest.getUserName()).isPresent()) {

            UserDetailsImpl builtUser = UserDetailsImpl.build(userRepository.findByUsername(signinRequest.getUserName()).get());
            builtUser.setPassword(passwordEncoder.encode(builtUser.getPassword()));

            String jwt = jwtCore.generateToken(builtUser);
            EntryPoint.currentUser = builtUser;

            return ResponseEntity.ok(jwt);
        }

        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/authenticated/updateProfilePhoto")
    public ResponseEntity<?> uploadImage(@RequestParam("image")MultipartFile file) throws IOException {
        String uploadImage = storageService.uploadImage(file);
        return ResponseEntity.ok().body(uploadImage);
    }

    @GetMapping("/authenticated/getProfilePhoto")
    public ResponseEntity<?> downloadImage() {
        byte[] imageData = storageService.downloadImage();
        if (imageData == null) return ResponseEntity.ok().body("У вас нет загруженных фото");
        return ResponseEntity.ok().contentType(MediaType.valueOf("image/png")).body(imageData);
    }
}
