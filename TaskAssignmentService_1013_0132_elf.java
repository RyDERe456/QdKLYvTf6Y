// 代码生成时间: 2025-10-13 01:32:32
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Optional;

// TaskAssignmentService is a Spring Boot service component responsible for task assignment logic.
@Service
public class TaskAssignmentService {

    // Autowiring the TaskRepository to access the database operations for tasks.
    @Autowired
    private TaskRepository taskRepository;

    // Assigns a task to a user.
    // @param taskId The ID of the task to be assigned.
    // @param userId The ID of the user to whom the task will be assigned.
    public Task assignTask(Long taskId, Long userId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found"));

        if (!task.isAssignable()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Task cannot be assigned"));
        }

        task.assignToUser(userId);
        return taskRepository.save(task);
    }

    // Retrieves all tasks assigned to a user.
    // @param userId The ID of the user whose tasks need to be retrieved.
    // @return A list of tasks assigned to the user.
    public List<Task> getTasksAssignedToUser(Long userId) {
        return taskRepository.findTasksAssignedToUser(userId);
    }

    // Retrieves a single task by ID.
    // @param taskId The ID of the task to be retrieved.
    // @return The task object if found, otherwise throws an exception.
    public Task getTaskById(Long taskId) {
        return taskRepository.findById(taskId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found"));
    }

    // Adds a new task to the system.
    // @param task The task object to be added.
    // @return The newly created task object.
    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    // Updates an existing task.
    // @param taskId The ID of the task to be updated.
    // @param updatedTask The updated task object.
    // @return The updated task object.
    public Task updateTask(Long taskId, Task updatedTask) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found"));

        task.setTitle(updatedTask.getTitle());
        task.setDescription(updatedTask.getDescription());
        // other fields can be updated similarly
        return taskRepository.save(task);
    }

    // Deletes a task by ID.
    // @param taskId The ID of the task to be deleted.
    public void deleteTask(Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found"));
        taskRepository.delete(task);
    }
}
