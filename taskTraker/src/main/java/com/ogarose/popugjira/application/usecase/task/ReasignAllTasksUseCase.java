package com.ogarose.popugjira.application.usecase.task;

import com.ogarose.popugjira.domain.todo.Task;
import com.ogarose.popugjira.domain.todo.TaskExtractor;
import com.ogarose.popugjira.domain.todo.TaskPersister;
import com.ogarose.popugjira.domain.user.User;
import com.ogarose.popugjira.domain.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

@Service
@AllArgsConstructor
public class ReasignAllTasksUseCase implements ReasignAllTasks {
    private final TaskExtractor taskExtractor;
    private final TaskPersister taskPersister;
    private final UserRepository userRepository;
    private final Random randomNumber = new Random();

    @Override
    public void execute() {
        List<User> allUsers = userRepository.findAll();
        List<Task> openedTask = taskExtractor.findAllOpened();

        ListIterator<Task> taskIterator = openedTask.listIterator();
        Iterator<User> userIterator = allUsers.iterator();

        User userToAssign = null;
        int numToAssign = 0;
        while (taskIterator.hasNext()) {
            int index = taskIterator.nextIndex();
            Task currentTask = taskIterator.next();

            if (numToAssign == 0) {
                userToAssign = userIterator.next();
                if (userIterator.hasNext()) {
                    int max = openedTask.size() - index;
                    numToAssign = max == 1 ? 1 : getRandomNumber(1, openedTask.size() - index);
                } else {
                    numToAssign = openedTask.size() - index;
                }
            }

            currentTask.assignTo(userToAssign);
            taskPersister.save(currentTask);

            numToAssign--;
        }
    }

    public int getRandomNumber(int min, int max) {
        return randomNumber.nextInt(max - min) + min;
    }
}
