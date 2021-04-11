package com.ogarose.popugjira.presentation.web;

import com.ogarose.popugjira.application.usecase.task.command.TaskCommand;
import com.ogarose.popugjira.application.usecase.task.CloseTask;
import com.ogarose.popugjira.application.usecase.task.CreateNew;
import com.ogarose.popugjira.application.usecase.task.ReasignAllTasks;
import com.ogarose.popugjira.application.usecase.task.ReopenTask;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@AllArgsConstructor
public class TaskController {
    private final CreateNew createNewTaskUseCase;
    private final CloseTask closeTaskUseCase;
    private final ReopenTask reopenTaskUseCase;
    private final ReasignAllTasks reasignAllTasksUseCase;

    @GetMapping("/task/add")
    public String add(Model model) {
        model.addAttribute("task", new TaskCommand());

        return "task/add";
    }

    @PostMapping("/task")
    public String saveOrUpdate(
            @Valid @ModelAttribute("task") TaskCommand taskCommand,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes
    ) {
        if (bindingResult.hasErrors()) {
            return "task/add";
        }

        TaskCommand handledTask = createNewTaskUseCase.createNew(taskCommand);
        redirectAttributes.addFlashAttribute("successAlert", "Task has been created. ID " + handledTask.getId());

        return "redirect:/todo";
    }

    @GetMapping("/task/{id}/close")
    public String close(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        closeTaskUseCase.close(id);

        redirectAttributes.addFlashAttribute("successAlert", "Task has been closed. ID " + id);

        return "redirect:/todo";
    }

    @GetMapping("/task/{id}/reopen")
    public String reopen(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        reopenTaskUseCase.reopen(id);

        redirectAttributes.addFlashAttribute("successAlert", "Task has been reopened. ID " + id);

        return "redirect:/todo";
    }

    @GetMapping("/task/reassign-all")
    public String reassignAll(RedirectAttributes redirectAttributes) {
        reasignAllTasksUseCase.execute();

        redirectAttributes.addFlashAttribute("successAlert", "All tasks have been reasign");

        return "redirect:/todo";
    }
}
