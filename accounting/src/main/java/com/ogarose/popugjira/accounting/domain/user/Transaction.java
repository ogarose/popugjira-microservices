package com.ogarose.popugjira.accounting.domain.user;

import com.ogarose.popugjira.accounting.domain.task.Task;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer debit;
    private Integer credit;
    private String description;

    @ManyToOne
    @JoinColumn(name = "task_id", nullable = true)
    private Task task;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
