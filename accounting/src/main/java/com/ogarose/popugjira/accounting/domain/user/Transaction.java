package com.ogarose.popugjira.accounting.domain.user;

import com.ogarose.popugjira.accounting.domain.task.Task;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer debit;
    private Integer credit;
    private String description;
    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "task_id", nullable = true)
    private Task task;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Transaction(Integer credit, String description, Task task, User user) {
        this.credit = credit;
        this.description = description;
        this.task = task;
        this.user = user;
    }

    public Transaction(Integer debit, Integer credit, String description, Task task, User user) {
        this.debit = debit;
        this.credit = credit;
        this.description = description;
        this.task = task;
        this.user = user;
    }

    public static Transaction createCreditTaskTransaction(Integer credit, String description, Task task, User user) {
        return new Transaction(
                null,
                credit,
                description,
                task,
                user
        );
    }

    public static Transaction createCreditTransaction(Integer credit, String description, User user) {
        return new Transaction(
                null,
                credit,
                description,
                null,
                user
        );
    }

    public static Transaction createDebitTaskTransaction(Integer debit, String description, Task task, User user) {
        return new Transaction(
                debit,
                null,
                description,
                task,
                user
        );
    }
}
