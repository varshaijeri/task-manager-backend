package com.varsha.taskmanager.service;

import com.varsha.taskmanager.entity.Task;
import com.varsha.taskmanager.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }

    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    public ResponseEntity<Task> updateTask(Long id, Task taskData) {
        return taskRepository.findById(id).map(task -> {
            task.setTitle(taskData.getTitle());
            task.setDescription(taskData.getDescription());
            task.setDueDate(taskData.getDueDate());
            task.setTag(taskData.getTag());
            Task updatedTask = taskRepository.save(task);
            return ResponseEntity.ok(updatedTask);
        }).orElse(ResponseEntity.notFound().build());
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }
}
