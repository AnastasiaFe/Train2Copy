package ua.nure.fedorenko.kidstim.service;

import ua.nure.fedorenko.kidstim.model.entity.Child;
import ua.nure.fedorenko.kidstim.model.entity.Task;

import java.util.List;

public interface TaskService {

    Task getTaskById(String id);

    void saveTask(Task task);

    void deleteTask(String id);

    List<Task> getTasksByParent(String parent);

    void markAsCompleted(String taskId, boolean complete);

    List<Task> getTasksByChild(Child child);
}
