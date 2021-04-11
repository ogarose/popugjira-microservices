package com.ogarose.popugjira.application.usecase.task.command;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
public class TaskCommand {
    private Integer id;

    @NotEmpty
    private String title;
}
