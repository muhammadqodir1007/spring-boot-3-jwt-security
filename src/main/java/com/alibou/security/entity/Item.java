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

public class Item {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String description;

    private long quantity;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User user;


    @ManyToOne
    private ItemType itemType;

    @ManyToOne
    private Category category;


    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


}
