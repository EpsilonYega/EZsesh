package vibe.EZsesh.repositories;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import vibe.EZsesh.entities.AppUser;
import vibe.EZsesh.entities.FileAnswer;
import vibe.EZsesh.entities.ImageData;
import vibe.EZsesh.entities.Task;

import java.util.List;
import java.util.Optional;

public interface AnswerRepository extends JpaRepository<FileAnswer, Long> {
    Optional<List<ImageData>> findByTask(Task task);
    @Transactional
    @Modifying
    @Query("update FileAnswer fa set fa.name = ?1, fa.type = ?2, fa.imageData = ?3 where fa.task = ?4")
    void updateImageDataById(String name, String type, byte[] imageData, Task task_id);
}
