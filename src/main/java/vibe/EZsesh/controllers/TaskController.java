package vibe.EZsesh.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vibe.EZsesh.EntryPoint;
import vibe.EZsesh.entities.AppUser;
import vibe.EZsesh.entities.Task;
import vibe.EZsesh.repositories.TaskRepository;
import vibe.EZsesh.services.TaskService;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@Slf4j
public class TaskController {
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private TaskService taskService;

    @GetMapping("/findTaskById/{id}")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<Task> findTaskById(@PathVariable("id") long id) {
        return ResponseEntity.ok(taskService.findById(id));
    }
    @GetMapping("/findTaskById/{topic}")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<List<Task>> findTasksByTopic(@PathVariable("topic") String topic) {
        return ResponseEntity.ok(taskService.findByTopic(topic));
    }
    @GetMapping("/findTaskByCourse/{course}")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<List<Task>> findTasksByCourse(@PathVariable("course") byte course) {
        return ResponseEntity.ok(taskService.findByCourse(course));
    }
    @GetMapping("/findTaskByCourseAndSemester/{course}/{semester}")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<List<Task>> findTasksByCourseAndSemester(@PathVariable("course") byte course, @PathVariable("semester") byte semester) {
        return ResponseEntity.ok(taskService.findByCourseAndSemester(course, semester));
    }
    @GetMapping("/findTaskByAuthor/{author}")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<List<Task>> findTasksByAuthor(@PathVariable("author") String author) {
        return ResponseEntity.ok(taskService.findByAuthor(author));
    }
    @GetMapping("/findAll")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<List<Task>> findAllTasks() {
        return ResponseEntity.ok(taskService.findAll());
    }
    @PostMapping("/authenticated/newTask")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<?> addNewTask(@RequestBody Task task) {
        task.setAuthor(AppUser.builder()
                .id(EntryPoint.currentUser.getId())
                .username(EntryPoint.currentUser.getUsername())
                .email(EntryPoint.currentUser.getEmail())
                .password(EntryPoint.currentUser.getPassword())
                .resetPasswordCodeWord(EntryPoint.currentUser.getResetPasswordCodeWord())
                .role(EntryPoint.currentUser.getRole())
                .build());
        return ResponseEntity.ok(taskRepository.save(task));
    }
    @PostMapping("/authenticated/updateTask/{id}")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<?> updateTask(@RequestBody Task task, @PathVariable("id") long id) {
        log.info("Задача " + task.getTopic() + " была обновлена");
        taskService.updateTaskById(task, id);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/authenticated/deleteTask/{id}")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<?> deleteTask(@PathVariable("id") long id) {
        taskService.deleteTaskById(id);
        return ResponseEntity.ok().build();
    }
}
