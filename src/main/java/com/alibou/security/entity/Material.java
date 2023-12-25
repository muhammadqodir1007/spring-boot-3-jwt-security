package com.alibou.security.entity;

import com.alibou.security.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@RequiredArgsConstructor

public class Material {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String description;

    private long quantity;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User user;

    @ManyToOne
    private MaterialType materialType;

    @ManyToOne
    private MaterialCategory materialCategory;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
