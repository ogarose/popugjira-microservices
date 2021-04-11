package com.ogarose.popugjira.domain.todo;

import com.ogarose.popugjira.common.types.ValueObject;
import lombok.EqualsAndHashCode;
import lombok.Value;

@EqualsAndHashCode(callSuper = true)
@Value
public class TaskTitle extends ValueObject {
    String title;

    @Override
    public String toString() {
        return title;
    }
}
