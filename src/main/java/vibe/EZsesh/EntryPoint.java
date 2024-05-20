package vibe.EZsesh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import vibe.EZsesh.entities.UserDetailsImpl;

@SpringBootApplication
public class EntryPoint {
    public static UserDetailsImpl currentUser;
    public static void main(String[] args) {
        SpringApplication.run(EntryPoint.class);
    }
}
