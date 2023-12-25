package com.alibou.security.entity;

import com.alibou.security.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MaterialTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    private MaterialType itemType;

    @ManyToOne
    private User user;


    @ManyToOne
    private MaterialCategory category;
    private long quantity;
    private LocalDateTime actionDate;
    private String actionType;
}
