package com.ogarose.popugjira.accounting.domain.task;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Random;
import java.util.UUID;

@Entity(name = "Task")
@NoArgsConstructor
@Getter
public class Task {
    @Id
    @Column(columnDefinition = "BINARY(16)", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "open_price", nullable = false)
    private Integer openPrice;

    @Column(name = "close_price", nullable = false)
    private Integer closePrice;

    public Task(UUID id) {
        this.id = id;
        Random random = new Random();
        openPrice = random.nextInt(10) + 10;
        closePrice = random.nextInt(20) + 20;
    }
}
