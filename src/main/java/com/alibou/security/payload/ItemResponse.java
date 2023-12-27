package com.alibou.security.payload;

import com.alibou.security.entity.Category;
import com.alibou.security.entity.ItemType;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class ItemResponse {

    private int id;
    private String description;
    private long quantity;
    private UserDto userDto;
    private ItemType itemType;
    private Category category;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
