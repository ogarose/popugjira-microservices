package com.ogarose.popugjira.infrastructure.persistence.todo;

import com.ogarose.popugjira.domain.todo.Status;
import com.ogarose.popugjira.domain.user.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.AbstractAggregateRoot;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "Task")
@Getter
@Setter
public class TaskEntity extends AbstractAggregateRoot<TaskEntity> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @GeneratedValue()
    @Column(columnDefinition = "BINARY(16)", updatable = false, nullable = false)
    private UUID publicId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(columnDefinition = "BINARY(16)")
    private UUID assignToId;

    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @Column(name = "closed_at", nullable = true, updatable = true)
    private LocalDateTime closedAt;
}
