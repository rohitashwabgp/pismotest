package com.pismo.test.cards.domn;

import jakarta.persistence.*;

@Entity
@Table(name = "OperationType")
public class OperationType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    public String getDescription() {
        return description;
    }

    public Integer getId() {
        return id;
    }

    @Column(nullable = false)
    private String description;
}
