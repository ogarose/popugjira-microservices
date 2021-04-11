package com.ogarose.popugjira.infrastructure.messaging.eventspreader;

import com.ogarose.popugjira.common.messaging.MessageTopics;
import com.ogarose.popugjira.common.messaging.traker.biz.TaskAssigned;
import com.ogarose.popugjira.common.messaging.traker.biz.TaskClosed;
import com.ogarose.popugjira.common.messaging.traker.cud.TaskCreated;
import com.ogarose.popugjira.domain.todo.TaskEvent;
import com.ogarose.popugjira.infrastructure.messaging.MessageBus;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class TaskEventsSpreader {
    private final MessageBus messageBus;

    @EventListener(TaskEvent.Created.class)
    public void onTaskCreated(TaskEvent.Created event) {
        TaskCreated taskCreated = new TaskCreated(event.getTask().getId().getPublicId());

        messageBus.sendMessage(MessageTopics.TASK_CUD, taskCreated);
    }

    @EventListener(TaskEvent.Closed.class)
    public void onTaskClosed(TaskEvent.Closed event) {
        TaskClosed taskClosed = new TaskClosed(event.getTask().getId().getPublicId(), event.getTask().getAssignToId());

        messageBus.sendMessage(MessageTopics.TASK_BIZ, taskClosed);
    }

    @EventListener(TaskEvent.Assigned.class)
    public void onTaskAssigned(TaskEvent.Assigned event) {
        TaskAssigned taskAssigned = new TaskAssigned(event.getTask().getId().getPublicId(), event.getAssignedTo());
        messageBus.sendMessage(MessageTopics.TASK_BIZ, taskAssigned);
    }
}
