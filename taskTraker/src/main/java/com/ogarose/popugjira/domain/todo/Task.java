package com.ogarose.popugjira.domain.todo;

import com.ogarose.popugjira.domain.user.User;
import lombok.Getter;
import org.springframework.data.domain.AbstractAggregateRoot;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "Task")
@Getter
public class Task extends AbstractAggregateRoot<Task> {

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

    /**
     * @todo Task and User aggregate are independent. Rewrite just to user_id.
     */
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private User assignedTo;

    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @Column(name = "closed_at", nullable = true, updatable = true)
    private LocalDateTime closedAt;

    public Task() {
    }

    public Task(UUID publicId, String title) {
        this.publicId = publicId;
        this.title = title;
        this.status = Status.OPEN;
        this.createdAt = LocalDateTime.now();

        registerEvent(new TaskCreatedEvent(this.publicId, this));
    }

    public void close() {
        if (!status.canBeChangeTo(Status.CLOSED)) {
            throw new RuntimeException("Can no be change to closed");
        }

        status = Status.CLOSED;
        closedAt = LocalDateTime.now();

        registerEvent(new TaskClosedEvent(this.publicId, this));
    }

    public void reopen() {
        if (!status.canBeChangeTo(Status.OPEN)) {
            throw new RuntimeException("Can no be change to open");
        }

        status = Status.OPEN;
    }

    public void assignTo(User user) {
        assignedTo = user;

        registerEvent(new TaskAssignedEvent(this, this.publicId, user.getId()));
    }
}
