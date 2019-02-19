package ua.nure.fedorenko.kidstim.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.nure.fedorenko.kidstim.model.entity.Child;
import ua.nure.fedorenko.kidstim.model.entity.Task;
import ua.nure.fedorenko.kidstim.service.ChildService;
import ua.nure.fedorenko.kidstim.service.TaskService;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
public class TaskRestController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private ChildService childService;

    @PutMapping(value = "/updateTask")
    @ResponseStatus(HttpStatus.OK)
    public void updateTask(@RequestBody Task task) {
        taskService.saveTask(task);
    }

    @PostMapping(value = "/saveTask")
    @ResponseStatus(value = HttpStatus.CREATED)
    public void addTask(@RequestBody Task task) {
        taskService.saveTask(task);
    }

    @DeleteMapping(value = "/deleteTask")
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteTask(@NotNull @RequestParam("id") String id) {
        taskService.deleteTask(id);
    }


    @GetMapping(value = "/task")
    public ResponseEntity getTaskById(@NotNull @RequestParam("id") String id) {
        Task task = taskService.getTaskById(id);
        if (task == null) {
            return new ResponseEntity("No task found for ID " + id, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(task, HttpStatus.OK);
    }

    @GetMapping(value = "/tasksByParent")
    public List<Task> getTasksByParent(@NotNull @RequestParam("id") String parentId) {
       return taskService.getTasksByParent(parentId);
    }

    @GetMapping(value = "/tasksByChild")
    public List<Task> getTasksByChild(@NotNull @RequestParam("id") String childId) {
        Child child = childService.getChildById(childId);
        return taskService.getTasksByChild(child);

    }

    @PutMapping(value = "/markAsCompleted")
    public void markAsCompleted(@NotNull @RequestParam("id") String id, @RequestParam("complete") boolean complete) {
        taskService.markAsCompleted(id, complete);
    }
}
