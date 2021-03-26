package com.ogarose.popugjira.application;

import com.ogarose.popugjira.application.command.TaskCommand;
import com.ogarose.popugjira.application.exceptions.NotFoundException;
import com.ogarose.popugjira.application.mapper.TaskCommandMapper;
import com.ogarose.popugjira.domain.todo.Task;
import com.ogarose.popugjira.domain.todo.TaskRepository;
import com.ogarose.popugjira.domain.user.User;
import com.ogarose.popugjira.domain.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskCommandMapper taskCommandMapper;
    private final UserRepository userRepository;

    public List<Task> findAllOpened() {
        return taskRepository.findAllOpened();
    }

    public List<Task> findAllClosed() {
        return taskRepository.findAllClosed();
    }

    public List<Task> findAllAssignedToUser(User user) {
        return taskRepository.findAllOpened()
                .stream()
                .filter(task -> task.getAssignedTo() != null && task.getAssignedTo().equals(user))
                .collect(Collectors.toList());
    }

    public TaskCommand createNew(TaskCommand taskCommand) {
        Task task = new Task(UUID.randomUUID(),taskCommand.getTitle());
        Task savedTask = taskRepository.save(task);

        return taskCommandMapper.toCommand(savedTask);
    }

    public void close(Integer taskId) {
        Optional<Task> optionalTask = taskRepository.find(taskId);
        if (optionalTask.isEmpty()) {
            throw new NotFoundException();
        }

        Task task = optionalTask.get();
        task.close();

        taskRepository.save(task);
    }

    public void reopen(Integer taskId) {
        Optional<Task> optionalTask = taskRepository.find(taskId);
        if (optionalTask.isEmpty()) {
            throw new NotFoundException();
        }

        Task task = optionalTask.get();
        task.reopen();

        taskRepository.save(task);
    }

    public void reasignAll() {
        List<User> allUsers = userRepository.findAll();
        List<Task> openedTask = taskRepository.findAllOpened();

        ListIterator<Task> taskIterator = openedTask.listIterator();
        Iterator<User> userIterator = allUsers.iterator();

        User userToAssign = null;
        int numToAssign = 0;
        while (taskIterator.hasNext()) {
            int index = taskIterator.nextIndex();
            Task currentTask = taskIterator.next();

            if (numToAssign == 0) {
                userToAssign = userIterator.next();
                if(userIterator.hasNext()) {
                    int max = openedTask.size() - index;
                    numToAssign = max == 1 ? 1 : getRandomNumber(1, openedTask.size() - index);
                } else {
                    numToAssign = openedTask.size() - index;
                }
            }

            currentTask.assignTo(userToAssign);
            taskRepository.save(currentTask);

            numToAssign--;
        }
    }

    public int getRandomNumber(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }
}
