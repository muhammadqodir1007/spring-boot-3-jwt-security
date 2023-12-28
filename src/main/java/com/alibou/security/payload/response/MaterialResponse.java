package com.alibou.security.payload.response;

import com.alibou.security.entity.MaterialCategory;
import com.alibou.security.entity.MaterialType;
import com.alibou.security.payload.dto.UserDto;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MaterialResponse {

    private int id;
    private String description;
    private long quantity;
    private UserDto userDto;
    private MaterialType itemType;
    private MaterialCategory category;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
