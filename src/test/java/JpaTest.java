import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import vibe.EZsesh.EntryPoint;
import vibe.EZsesh.entities.AppUser;
import vibe.EZsesh.entities.Task;
import vibe.EZsesh.repositories.TaskRepository;

@SpringBootTest(classes = EntryPoint.class)
public class JpaTest {
    @Autowired
    private TaskRepository taskRepository;
    private Task task = new Task((long) 42, "Сдать тестирование", (byte) 3, (byte) 2, new AppUser((long) 42, "Влад", "vsorlov04@rostov-don.ithub.ru", "ahahah net", "xnj", "USER"), "Помогите");

    @Test
    public void testCreate() {
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
