package com.ogarose.popugjira.accounting.domain.task;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity(name = "Task")
public class Task {
    @Id
    @Column(columnDefinition = "BINARY(16)", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "open_price", nullable = false)
    private Long openPrice;

    @Column(name = "close_price", nullable = false)
    private Long closePrice;
}
