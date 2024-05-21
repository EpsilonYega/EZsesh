package vibe.EZsesh.repositories;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import vibe.EZsesh.entities.AppUser;
import vibe.EZsesh.entities.Task;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {
    Optional<Task> findById(long id);
    Optional<List<Task>> findByTopic(String topic);
    Optional<List<Task>> findByCourse(String course);
    @Transactional
    @Modifying
    @Query("select t from Task t where t.course = ?1 and t.semester = ?2")
    Optional<List<Task>> findByCourseAndSemester(String course, String semester);
    Optional<List<Task>> findByAuthor(String author);
    List<Task> findAll();
    @Transactional
    @Modifying
    @Query("update Task t set t.topic = ?1, t.course = ?2, t.semester = ?3, t.author = ?4, t.stringAnswer = ?5 where t.id = ?6")
    void updateTaskById(String topic, byte course, byte semester, AppUser author, String stringAnswer, long id);
    @Transactional
    void deleteTaskById(long id);
}
