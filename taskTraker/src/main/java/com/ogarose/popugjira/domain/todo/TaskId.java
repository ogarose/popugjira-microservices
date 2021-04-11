package com.ogarose.popugjira.domain.todo;

import com.ogarose.popugjira.common.types.EntityId;
import com.ogarose.popugjira.common.types.ValueObject;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
public class TaskId extends EntityId {
    private final TaskInternalId internalId;
    private final UUID publicId;

    public TaskId(TaskInternalId id, UUID publicId) {
        this.internalId = id;
        this.publicId = publicId;
    }

    public boolean sameAs(TaskId taskId) {
        return this.internalId.equals(taskId.internalId) && publicId.equals(taskId.publicId);
    }

    @Getter
    @AllArgsConstructor
    public static class TaskInternalId extends ValueObject {
        private final Integer internalId;

        public Integer value() {
            return internalId;
        }
    }
}


