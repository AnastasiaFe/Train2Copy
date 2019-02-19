package ua.nure.fedorenko.kidstim.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.nure.fedorenko.kidstim.model.entity.Child;
import ua.nure.fedorenko.kidstim.model.entity.Task;
import ua.nure.fedorenko.kidstim.model.entity.TaskStatus;
import ua.nure.fedorenko.kidstim.model.repository.TaskRepository;
import ua.nure.fedorenko.kidstim.service.ChildService;
import ua.nure.fedorenko.kidstim.service.ParentService;
import ua.nure.fedorenko.kidstim.service.TaskService;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private ChildService childService;
    @Autowired
    private ParentService parentService;

    @Override
    public Task getTaskById(String id) {
        Optional<Task> task = taskRepository.findById(id);
        return task.orElse(null);
    }

    public void saveTask(Task task) {
        taskRepository.save(task);
    }

    @Override
    public void deleteTask(String id) {
        Task task = getTaskById(id);
        taskRepository.delete(task);
    }

    @Override
    public List<Task> getTasksByParent(String parentId) {
        return taskRepository.findByParent(parentService.getParentById(parentId));
    }

    @Override
    public void markAsCompleted(String taskId, boolean complete) {
        Task task = getTaskById(taskId);
        List<Child> children = task.getChildren();
        if (complete) {
            task.setStatus(TaskStatus.COMPLETED);
            for (Child child : children) {
                child.setPoints(child.getPoints() + task.getPoints());
                childService.updateChild(child);
            }
        } else {
            task.setStatus(TaskStatus.CREATED);
            for (Child child : children) {
                child.setPoints(child.getPoints() - task.getPoints());
                childService.updateChild(child);
            }
        }

        saveTask(task);
    }

    @Override
    public List<Task> getTasksByChild(Child child) {
        return taskRepository.getTasksByChild(child);
    }
}
