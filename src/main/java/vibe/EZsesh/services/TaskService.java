package vibe.EZsesh.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vibe.EZsesh.EntryPoint;
import vibe.EZsesh.entities.AppUser;
import vibe.EZsesh.entities.Task;
import vibe.EZsesh.repositories.TaskRepository;
import java.util.List;

@Service
public class TaskService {
    @Autowired
    TaskRepository repository;
    public Task findById(long id) {
        return repository.findById(id).orElseThrow();
    }
    public List<Task> findByTopic(String topic) {
        return repository.findByTopic(topic).orElseThrow();
    }
    public List<Task> findByCourse(String course) {
        return repository.findByCourse(course).orElseThrow();
    }
    public List<Task> findByCourseAndSemester(String course, String semester) {
        return repository.findByCourseAndSemester(course, semester).orElseThrow();
    }
    public List<Task> findByAuthor(String author) {
        return repository.findByAuthor(author).orElseThrow();
    }
    public List<Task> findAll() {
        return repository.findAll();
    }
    public void updateTaskById(Task newTask, long id) {
        Task task = repository.findById(id).orElseThrow();
        if ((!(newTask.getTopic() == null || newTask.getTopic().isEmpty()))) task.setTopic(newTask.getTopic());

        if ((!(newTask.getCourse() == 0))) task.setCourse(newTask.getCourse());

        if ((!(newTask.getSemester() == 0))) task.setSemester(newTask.getSemester());

        if ((!(newTask.getAuthor() == null))) task.setAuthor(AppUser.builder()
                .id(EntryPoint.currentUser.getId())
                .username(EntryPoint.currentUser.getUsername())
                .email(EntryPoint.currentUser.getEmail())
                .password(EntryPoint.currentUser.getPassword())
                .resetPasswordCodeWord(EntryPoint.currentUser.getResetPasswordCodeWord())
                .role(EntryPoint.currentUser.getRole())
                .build());

        if ((!(newTask.getStringAnswer() == null || newTask.getStringAnswer().isEmpty()))) task.setStringAnswer(newTask.getStringAnswer());

        repository.updateTaskById(task.getTopic(), task.getCourse(), task.getSemester(), task.getAuthor(), task.getStringAnswer(), id);
    }
    public void deleteTaskById(long id) {
        repository.deleteTaskById(id);
    }
}
