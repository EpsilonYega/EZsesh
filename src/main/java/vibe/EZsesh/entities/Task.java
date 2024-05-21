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
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "topic")
    private String topic;
    @Column(name = "course")
    private byte course;
    @Column(name = "semester")
    private byte semester;
    @ManyToOne
    @JoinColumn(name = "app_user_id")
    private AppUser author;
    @Column(name = "stringAnswer")
    private String stringAnswer;
}
