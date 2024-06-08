import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import vibe.EZsesh.EntryPoint;
import vibe.EZsesh.entities.AppUser;
import vibe.EZsesh.entities.Task;
import vibe.EZsesh.repositories.TaskRepository;
import vibe.EZsesh.repositories.UserRepository;

import java.util.Objects;
import java.util.Optional;

@SpringBootTest(classes = EntryPoint.class)
public class JpaTest {
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private UserRepository userRepository;
    private AppUser user = new AppUser("Владос", "vsorlov04@rostov-don.ithub.ru", "ahahah net", "xnj", "USER");
    private Task task = new Task((long) 42, "Сдать тестирование", (byte) 3, (byte) 2, null, "Помогите");

    public void initUser() {
        userRepository.save(user);
    }

    @Test
    public Optional<AppUser> returnUser() {
        try {
            return userRepository.findByUsername("Владос");
        }
        catch (Exception e) {
            return Optional.empty();
        }
    }

    @Test
    public void testCreate() {
        if (Objects.equals(returnUser(), Optional.empty())) {
            initUser();
        }
        task.setAuthor(userRepository.findByUsername("Владос").get());
        taskRepository.save(task);
    }
    @Test
    public void testRead() {
        testCreate();
        taskRepository.findAll();
    }
    @Test
    public void testUpdate() {
        testCreate();
        taskRepository.updateTaskById("Сдать тестирование на 90 баллов", task.getCourse(), task.getSemester(), task.getAuthor(), "Пожалуйста доставьте 50 баллов", task.getId());
    }
    @Test
    public void testDelete() {
        testCreate();
        taskRepository.deleteTaskById(task.getId());
    }
}
