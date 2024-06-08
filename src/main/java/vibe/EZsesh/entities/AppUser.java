package vibe.EZsesh.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="username")
    private String username;
    @Column(name="email")
    private String email;
    @Column(name="password")
    private String password;
    @Column(name="resetpasswordcodeword")
    private String resetPasswordCodeWord;
    @Column(name="role")
    private String role;

    public AppUser(String username, String email, String password, String resetPasswordCodeWord, String role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.resetPasswordCodeWord = resetPasswordCodeWord;
        this.role = role;
    }
}
